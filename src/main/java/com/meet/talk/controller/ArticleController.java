package com.meet.talk.controller;

import com.meet.talk.domain.Article;
import com.meet.talk.domain.ResponseResult;
import com.meet.talk.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * (Article)表控制层
 *
 * @author makejava
 * @since 2022-07-27 10:32:05
 */
@RestController
@Api(tags = "文章接口")
public class ArticleController {
    /**
     * 服务对象
     */
    @Resource
    private ArticleService articleService;

    @PostMapping("/article")
    @ApiOperation("发表文章(根据用户id发表文章)")
    public ResponseResult sendArticle(@RequestBody @Valid Article article){
        return articleService.sendArticle(article);
    }
    @GetMapping("/article/{uId}")
    @ApiOperation("默认获取热门文章列表")
    public ResponseResult getArticle(@PathVariable("uId")Long uId){
        return articleService.getArticle(uId);
    }
    @GetMapping("/articleNew")
    @ApiOperation("获取最新文章列表")
    public ResponseResult getNewArticle(){
        return articleService.getNewArticle();
    }


    @GetMapping("/articleDetails/{aId}/{uId}")
    @ApiOperation("文章详情")
    public ResponseResult getArticleDetails(@PathVariable("aId") Long aId,@PathVariable("uId") Long uId){
        return  articleService.getArticleDetails(aId,uId);
    }
    @GetMapping("/followArticle/{uId}")
    @ApiOperation(value = "查询关注的用户发布的文章")
    public ResponseResult followArticle(@PathVariable("uId") Long uId){
        return articleService.followArticle(uId);
    }
}

