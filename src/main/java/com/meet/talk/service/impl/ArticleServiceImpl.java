package com.meet.talk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meet.talk.domain.*;

import com.meet.talk.mapper.*;
import com.meet.talk.service.ArticleService;
import com.meet.talk.utils.BeanCopyUtils;
import javafx.scene.control.Label;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * (Article)表服务实现类
 *
 * @author makejava
 * @since 2022-07-27 10:34:46
 */
@Service("articleService")
@Slf4j
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Resource
    private ArticleMapper articleMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private CommentMapper commentMapper;
    @Resource
    private FavoritesMapper favoritesMapper;
    @Resource
    private FavoritesArticleMapper favoritesArticleMapper;
    @Resource
    private PraiseMapper praiseMapper;
    @Resource
    private AttentionMapper attentionMapper;
    @Override

    public ResponseResult sendArticle(Article article) {

        articleMapper.insert(article);
        return ResponseResult.okResult();
    }



    @Override
    public ResponseResult getArticleDetails(Long aId,Long uId) {
        Article article = articleMapper.selectById(aId);
        User user = userMapper.selectById(article.getUId());

        LambdaQueryWrapper<Comment> commentWrapper=new LambdaQueryWrapper<>();
        LambdaQueryWrapper<Praise> praiseLambdaQueryWrapper=new LambdaQueryWrapper<>();
        praiseLambdaQueryWrapper
                .eq(Praise::getAId,aId);
        int praiseNum = praiseMapper.selectCount(praiseLambdaQueryWrapper);
        praiseLambdaQueryWrapper.eq(Praise::getUId,uId);
        //该用户对文章点赞了
        if ( praiseMapper.selectCount(praiseLambdaQueryWrapper)>0){
            article.setPraise(true);
        }
        //该用户是否收藏过
        LambdaQueryWrapper<Favorites> favoritesLambdaQueryWrapper=new LambdaQueryWrapper<>();
        LambdaQueryWrapper<FavoritesArticle> favoritesArticleLambdaQueryWrapper=new LambdaQueryWrapper<>();
        List<Long> fIds=new ArrayList<>();
        List<Long> aIds=new ArrayList<>();
        //获取当前用户的 fID集合
        favoritesLambdaQueryWrapper
                .eq(Favorites::getUId,uId)
                .select(Favorites::getFId);
        List<Favorites> favorites = favoritesMapper.selectList(favoritesLambdaQueryWrapper);
        favorites.forEach(favorites1 -> fIds.add(favorites1.getFId()));

        if (fIds.size()!=0){
            //获取fID对应的aId集合
            favoritesArticleLambdaQueryWrapper
                    .in(FavoritesArticle::getFId,fIds)
                    .select(FavoritesArticle::getAId);
            List<FavoritesArticle> favoritesArticles = favoritesArticleMapper.selectList(favoritesArticleLambdaQueryWrapper);

            favoritesArticles.forEach(favoritesArticles1 -> aIds.add(favoritesArticles1.getAId()));

        }
        //判断 当前文章是否存在于aIds
        if (aIds.contains(aId)){
            article.setFavorites(true);
        }

        //评论数量
        commentWrapper.eq(Comment::getAId,article.getAId());
        int count = commentMapper.selectCount(commentWrapper);

        article.setPraiseNum((long)praiseNum);
        article.setCommentNum((long) count);
        article.setName(user.getName());
        article.setAvatar(user.getAvatar());

        return ResponseResult.okResult(article);
    }
    @Override
    public ResponseResult getArticle(Long uId) {


        //TODO 还没有实现热门文章查询  初步思路: 根据一个时间范围和点赞量和评论 赋予一个权重
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(Article::getCreateTime);


        return ResponseResult.okResult(addArticleParam( list(queryWrapper), uId));
    }
    @Override
    public ResponseResult getNewArticle() {
        LambdaQueryWrapper<Article> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getCreateTime);
        return ResponseResult.okResult(addArticleParam( list(queryWrapper),0L));
    }

    @Override
    public ResponseResult followArticle(Long uId) {
        LambdaQueryWrapper<Attention> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper
                .eq(Attention::getUId,uId)
                .select(Attention::getFollowUId);
        List<Attention> attentions = attentionMapper.selectList(queryWrapper);
        List<Long> followUIds=new ArrayList<>();;
        attentions.forEach(follow->followUIds.add(follow.getFollowUId()));

        //查询关注的用户发布的文章
        LambdaQueryWrapper<Article> articleLambdaQueryWrapper=new LambdaQueryWrapper<>();
        articleLambdaQueryWrapper
                .in(Article::getUId,followUIds);
        List<Article> articles = articleMapper.selectList(articleLambdaQueryWrapper);
        return ResponseResult.okResult(addArticleParam(articles,uId));


    }

    public List<Article> addArticleParam(List<Article> articleList,Long uId){
        List<Long> fIds = new ArrayList<>();
        List<Long> aIds = new ArrayList<>();
        List<Long> praiseAIds=new ArrayList<>();
        //为每一个文章添加上 name和avatar
        //并且为每一个文章加上 commentNum  评论数量
        //判断该文章是否已经收藏过
        LambdaQueryWrapper<Favorites> favoritesLambdaQueryWrapper=new LambdaQueryWrapper<>();
        LambdaQueryWrapper<FavoritesArticle> favoritesArticleLambdaQueryWrapper=new LambdaQueryWrapper<>();
        LambdaQueryWrapper<Praise> praiseLambdaQueryWrapper=new LambdaQueryWrapper<>();

        //获取当前用户的 fID集合
        favoritesLambdaQueryWrapper
                .eq(Favorites::getUId,uId)
                .select(Favorites::getFId);
        List<Favorites> favorites = favoritesMapper.selectList(favoritesLambdaQueryWrapper);
        favorites.forEach(favorites1 -> fIds.add(favorites1.getFId()));
        if (fIds.size()!=0){
            //获取fID对应的aId集合
            favoritesArticleLambdaQueryWrapper
                    .in(FavoritesArticle::getFId,fIds)
                    .select(FavoritesArticle::getAId);
            List<FavoritesArticle> favoritesArticles = favoritesArticleMapper.selectList(favoritesArticleLambdaQueryWrapper);

            favoritesArticles.forEach(favoritesArticles1 -> aIds.add(favoritesArticles1.getAId()));

        }
        //获取用户点赞过的文章集合
        praiseLambdaQueryWrapper
                .eq(Praise::getUId,uId);
        List<Praise> praiseArticles = praiseMapper.selectList(praiseLambdaQueryWrapper);
        praiseArticles.forEach(a1->praiseAIds.add(a1.getAId()));
        for (Article article : articleList) {
            //只要该用户存在收藏夹收藏了此文章就设置将该文章的ifFavo设置为1
            if (aIds.contains(article.getAId())){
                article.setFavorites(true);
            }

            if (praiseAIds.contains(article.getAId())){
                article.setPraise(true);
            }
            praiseLambdaQueryWrapper=new LambdaQueryWrapper<>();
            praiseLambdaQueryWrapper
                    .eq(Praise::getAId,article.getAId());
            int count1 = praiseMapper.selectCount(praiseLambdaQueryWrapper);
            article.setPraiseNum((long) count1);


            User user = userMapper.selectById(article.getUId());
            article.setAvatar(user.getAvatar());
            article.setName(user.getName());
            LambdaQueryWrapper<Comment> commentWrapper=new LambdaQueryWrapper<>();
            commentWrapper.eq(Comment::getAId,article.getAId());
            int count = commentMapper.selectCount(commentWrapper);

            article.setCommentNum((long)count);
        }

        return  articleList;
    }


}
