package com.meet.talk.controller;

import com.meet.talk.domain.Article;
import com.meet.talk.domain.ResponseResult;
import com.meet.talk.domain.User;
import com.meet.talk.domain.vo.HistoryArticleVo;
import com.meet.talk.service.HistoryService;
import com.meet.talk.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @Author: alyosha
 * @Date: 2022/7/29 20:53
 */
@RestController
@Api(tags = "用户历史操作")
public class HistoryController {


    @Resource
    private HistoryService historyService;

    @PostMapping("/historyArticle")
    @ApiOperation(value = "用户点击文章后缓存到redis")
    public ResponseResult historyArticle(@RequestBody @Valid HistoryArticleVo historyArticleVo){
        return  historyService.historyArticle(historyArticleVo);
    }
    @PostMapping("/historyArticle/{uId}")
    @ApiOperation(value = "用户历史文章浏览")
    public ResponseResult historyArticleList(@PathVariable("uId")  Long uId){
        return  historyService.historyArticleList(uId);
    }

}
