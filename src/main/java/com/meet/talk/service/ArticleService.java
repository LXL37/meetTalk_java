package com.meet.talk.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.meet.talk.domain.Article;
import com.meet.talk.domain.ResponseResult;

import java.util.List;

/**
 * (Article)表服务接口
 *
 * @author makejava
 * @since 2022-07-27 10:32:05
 */
public interface ArticleService extends IService<Article> {


    ResponseResult sendArticle(Article article);

    ResponseResult getArticle(Long uId);

    ResponseResult getArticleDetails(Long aId,Long uId);


    ResponseResult getNewArticle();

    ResponseResult followArticle(Long uId);

    ResponseResult deleteArticle(Long aId,Long uId);
}
