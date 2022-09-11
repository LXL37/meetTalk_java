package com.meet.talk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.meet.talk.domain.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meet.talk.mapper.ArticleMapper;
import com.meet.talk.mapper.AttentionMapper;
import com.meet.talk.mapper.UserMapper;
import com.meet.talk.service.AttentionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * (Attention)表服务实现类
 *
 * @author makejava
 * @since 2022-08-06 08:54:03
 */
@Service
public class AttentionServiceImpl extends ServiceImpl<AttentionMapper, Attention> implements AttentionService {

    @Resource
    private AttentionMapper attentionMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private ArticleMapper articleMapper;

    @Override
    public ResponseResult userFollow(Long uId) {
        //todo 关注列表可以用redis进行缓存

        //查询用户关注的列表
        LambdaQueryWrapper<Attention> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper
                .eq(Attention::getUId,uId)
                .select(Attention::getFollowUId);
        List<Attention> attentions = attentionMapper.selectList(queryWrapper);
        List<Long> followIds=new ArrayList<>();;
        attentions.forEach(followId->followIds.add(followId.getFollowUId()));
        //联合user表查询关注的用户的头像 姓名 个性签名 uId
        return ResponseResult.okResult(addUsers(followIds));
    }

    @Override
    public ResponseResult deleteFollow(Long uId, Long followUId) {

        LambdaQueryWrapper<Attention> queryWrapper=new LambdaQueryWrapper<>();

        queryWrapper
                .eq(Attention::getUId,uId)
                .eq(Attention::getFollowUId,followUId);
        attentionMapper.delete(queryWrapper);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult userFans(Long uId) {
        LambdaQueryWrapper<Attention> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper
                .eq(Attention::getFollowUId,uId)
                .select(Attention::getUId);
        List<Attention> attentions = attentionMapper.selectList(queryWrapper);
        List<Long> fansUIds=new ArrayList<>();
        attentions.forEach(fans->fansUIds.add(fans.getUId()));
        //联合user表查询关注的用户的头像 姓名 个性签名 uId


        return ResponseResult.okResult(addUsers(fansUIds));
    }

    @Override
    public ResponseResult addFollow(Long uId, Long followUId) {


        Attention a=new Attention();
        a.setUId(uId);
        a.setFollowUId(followUId);
        attentionMapper.insert(a);
        return ResponseResult.okResult();

    }


    public  List<User> addUsers(List<Long> ids){
        LambdaQueryWrapper<User> userLambdaQueryWrapper=new LambdaQueryWrapper<>();
        userLambdaQueryWrapper
                .in(User::getUId,ids)
                .select(User::getName,User::getAvatar,User::getSignature,User::getUId);
        List<User> users = userMapper.selectList(userLambdaQueryWrapper);
            return users;

    }
}
