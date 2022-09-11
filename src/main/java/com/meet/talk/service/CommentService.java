package com.meet.talk.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.meet.talk.domain.Comment;
import com.meet.talk.domain.ResponseResult;

import java.util.List;

/**
 * (Comment)表服务接口
 *
 * @author makejava
 * @since 2022-07-27 10:42:49
 */
public interface CommentService extends IService<Comment> {

    /**
     * 发送消息
     * @param comment
     * @return
     */
    ResponseResult sendMsg(Comment comment);

    ResponseResult getComments(Long aId);
}
