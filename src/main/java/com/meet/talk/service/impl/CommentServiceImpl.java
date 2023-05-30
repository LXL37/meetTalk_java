package com.meet.talk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.meet.talk.domain.Comment;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meet.talk.domain.ResponseResult;
import com.meet.talk.domain.User;
import com.meet.talk.mapper.CommentMapper;
import com.meet.talk.mapper.UserMapper;
import com.meet.talk.service.CommentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import java.util.function.Supplier;

/**
 * (Comment)表服务实现类
 *
 * @author makejava
 * @since 2022-07-27 10:46:14
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Resource
    private CommentMapper commentMapper;
    @Resource
    private UserMapper userMapper;

    @Override
    public ResponseResult sendMsg(Comment comment) {

        System.out.println(comment);

        commentMapper.insert(comment);

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getComments(Long aId) {
        CompletableFuture<List<Comment>> rootComments=CompletableFuture.supplyAsync(new Supplier<List<Comment>>() {
            @Override
            public List<Comment> get() {
                LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(Comment::getRoot, 1);
                queryWrapper.eq(Comment::getAId,aId);
                List<Comment> rootComments = list(queryWrapper);
                return rootComments;
            }
        });
        CompletableFuture<List<Comment>> comments=CompletableFuture.supplyAsync(new Supplier<List<Comment>>() {
            @Override
            public List<Comment> get() {
                LambdaQueryWrapper<Comment> queryWrapper1 = new LambdaQueryWrapper<>();
                queryWrapper1.eq(Comment::getRoot, 0);
                queryWrapper1.eq(Comment::getAId,aId);
                List<Comment> comments = list(queryWrapper1);
                return comments;
            }
        });
        CompletableFuture<List<Comment>> processComments=rootComments.thenCombine(comments, new BiFunction<List<Comment>, List<Comment>, List<Comment>>() {
            @Override
            public List<Comment> apply(List<Comment> rootComments, List<Comment> comments) {
                for (Comment rootComment : rootComments) {
                    rootComment.setChildComments(new ArrayList<>());

                    User user = userMapper.selectById(rootComment.getUId());
                    rootComment.setName(user.getName());
                    rootComment.setAvatar(user.getAvatar());

                    List<Comment> childComments = rootComment.getChildComments();
                    for (Comment comment : comments) {
                        //为每一个子评论设置 头像名字 和 回复名 节省资源加个判断
                        if (comment.getName() == null) {
                            User user1 = userMapper.selectById(comment.getUId());
                            comment.setName(user1.getName());
                            comment.setAvatar(user1.getAvatar());
                            String toName = userMapper.selectById(comment.getParentUId()).getName();
                            comment.setToName(toName);
                        }
                        if (comment.getRootCId().equals(rootComment.getCId())) {
                            childComments.add(comment);
                        }
                    }
                }
                return rootComments;
            }
        });
        /*
        //根评论
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getRoot, 1);
        queryWrapper.eq(Comment::getAId,aId);
        List<Comment> rootComments = list(queryWrapper);
        //不是根评论的
        LambdaQueryWrapper<Comment> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(Comment::getRoot, 0);
        queryWrapper1.eq(Comment::getAId,aId);
        List<Comment> comments = list(queryWrapper1);

        //为每一个comment设置 头像和名字

        for (Comment rootComment : rootComments) {
            rootComment.setChildComments(new ArrayList<>());

            User user = userMapper.selectById(rootComment.getUId());
            rootComment.setName(user.getName());
            rootComment.setAvatar(user.getAvatar());

            List<Comment> childComments = rootComment.getChildComments();
            for (Comment comment : comments) {
                //为每一个子评论设置 头像名字 和 回复名 节省资源加个判断
                if (comment.getName()==null){
                    User user1 = userMapper.selectById(comment.getUId());
                    comment.setName(user1.getName());
                    comment.setAvatar(user1.getAvatar());
                    String toName = userMapper.selectById(comment.getParentUId()).getName();
                    comment.setToName(toName);
                }
                if (comment.getRootCId().equals(rootComment.getCId())) {
                    childComments.add(comment);
                }
            }
        }
        return ResponseResult.okResult(rootComments);*/
        return ResponseResult.okResult(processComments.join());
    }
}
