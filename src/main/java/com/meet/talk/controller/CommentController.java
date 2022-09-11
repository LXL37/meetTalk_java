package com.meet.talk.controller;

import com.meet.talk.domain.Comment;
import com.meet.talk.domain.ResponseResult;
import com.meet.talk.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * (Comment)表控制层
 *
 * @author makejava
 * @since 2022-07-27 10:42:49
 */

@Api(tags = "评论接口")
@RestController
public class CommentController {
    /**
     * 服务对象
     */
    @Resource
    private CommentService commentService;

    @GetMapping("/comment/{aId}")
    @ApiOperation(value = "获取文章内的评论")
    public ResponseResult getComments(@PathVariable("aId") Long aId ){
        return  commentService.getComments(aId);
    }
    @PostMapping("/comment")
    @ApiOperation("发表评论")
    public ResponseResult sendMsg(@RequestBody @Valid Comment comment) throws IOException {
        return commentService.sendMsg(comment);
    }

}

