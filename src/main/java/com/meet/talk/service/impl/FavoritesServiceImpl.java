package com.meet.talk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.meet.talk.domain.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meet.talk.mapper.ArticleMapper;
import com.meet.talk.mapper.FavoritesArticleMapper;
import com.meet.talk.mapper.FavoritesMapper;
import com.meet.talk.mapper.UserMapper;
import com.meet.talk.service.FavoritesService;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * (Favorites)表服务实现类
 *
 * @author makejava
 * @since 2022-08-04 17:02:43
 */
@Service
public class FavoritesServiceImpl extends ServiceImpl<FavoritesMapper, Favorites> implements FavoritesService {

    @Resource
    private FavoritesMapper favoritesMapper;

    @Resource
    private FavoritesArticleMapper favoritesArticleMapper;
    @Resource
    private ArticleMapper articleMapper;
    @Resource
    private UserMapper userMapper;

    @Override
    public ResponseResult userFavorites(Long uId) {
        LambdaQueryWrapper<Favorites> favoritesLambdaQueryWrapper = new LambdaQueryWrapper<>();
        favoritesLambdaQueryWrapper.eq(Favorites::getUId, uId);
        List<Favorites> favorites = list(favoritesLambdaQueryWrapper);
        return ResponseResult.okResult(favorites);
    }

    @Override
    public ResponseResult articleFavorites(Long fId) {
        LambdaQueryWrapper<FavoritesArticle> favoritesArticleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        favoritesArticleLambdaQueryWrapper.eq(FavoritesArticle::getFId, fId);
        favoritesArticleLambdaQueryWrapper.select(FavoritesArticle::getId, FavoritesArticle::getAId, FavoritesArticle::getCreateTime);
        List<FavoritesArticle> favoritesArticles = favoritesArticleMapper.selectList(favoritesArticleLambdaQueryWrapper);

        if (favoritesArticles.size() == 0) {
            return ResponseResult.okResult();
        } else {


            List<Date> favoritesDate = new LinkedList<>();
            List<Long> aId = new ArrayList<>();
            List<Long> favoriteArticleIds = new ArrayList<>();
            for (FavoritesArticle favoritesArticle : favoritesArticles) {
                aId.add(favoritesArticle.getAId());
                favoriteArticleIds.add(favoritesArticle.getId());
                favoritesDate.add(favoritesArticle.getCreateTime());
            }
            LambdaQueryWrapper<Article> articleLambdaQueryWrapper = new LambdaQueryWrapper<>();
            articleLambdaQueryWrapper
                    .select(Article::getTitle, Article::getAId, Article::getUId)
                    .in(Article::getAId, aId);
            List<Article> articles = articleMapper.selectList(articleLambdaQueryWrapper);

            int index = 0;
            for (Article article : articles) {
                User user = userMapper.selectById(article.getUId());
                article.setAvatar(user.getAvatar());
                article.setName(user.getName());
                //这里的createTime 不是文章的创建时间而是收藏的时间
                index = aId.indexOf(article.getAId());
                article.setFavoriteArticleId(favoriteArticleIds.get(index));
                article.setCreateTime(favoritesDate.get(index));

            }

            return ResponseResult.okResult(articles);
        }


    }

    @Override
    public ResponseResult addUserFavorites(Long aId, List<Long> fIds) {

        if (fIds.size() == 0) {
            return ResponseResult.errorResult(AppHttpCodeEnum.ERROR);
        } else {
            //添加之前进行 判断是否重复
            FavoritesArticle favoritesArticle = new FavoritesArticle();
            LambdaQueryWrapper<FavoritesArticle> queryWrapper = new LambdaQueryWrapper<>();


            queryWrapper.eq(FavoritesArticle::getAId, aId)
                    .in(FavoritesArticle::getFId, fIds)
                    .select(FavoritesArticle::getFId);
            List<FavoritesArticle> favoritesArticles = favoritesArticleMapper.selectList(queryWrapper);
            //去除重复的fid
            if (favoritesArticles != null) {
                for (FavoritesArticle article : favoritesArticles) {
                    fIds.remove(article.getFId());
                }

            }
            if (fIds.size() != 0) {
                for (Long fId : fIds) {
                    favoritesArticle.setAId(aId);
                    favoritesArticle.setFId(fId);
                    favoritesArticleMapper.insert(favoritesArticle);
                }
                return ResponseResult.okResult();
            } else {
                return ResponseResult.errorResult(AppHttpCodeEnum.FAVORITES_ERROR);
            }

        }


    }

    @Override
    public ResponseResult addFavorites(Favorites favorites) {
        if (favorites.getFName().equals("")) {
            return ResponseResult.errorResult(AppHttpCodeEnum.FAVORITES_NAME_NOT_NULL);
        } else {
            favoritesMapper.insert(favorites);
            return ResponseResult.okResult();

        }

    }

    @Override
    public ResponseResult deleteFavorites(Long fId) {
        //因为有主外键的关系  需要先删除 favorite article表
        LambdaQueryWrapper<FavoritesArticle> favoritesArticleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        favoritesArticleLambdaQueryWrapper
                .eq(FavoritesArticle::getFId, fId)
                .select(FavoritesArticle::getId);
        favoritesArticleMapper.delete(favoritesArticleLambdaQueryWrapper);
        favoritesMapper.deleteById(fId);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteFavoritesArticle(Long id) {
        System.out.println("文章id"+id);
        favoritesArticleMapper.deleteById(id);
        return ResponseResult.okResult();

    }
}
