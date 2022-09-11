package com.meet.talk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.BeanUtils;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.meet.talk.domain.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meet.talk.domain.vo.ArticleInfoVo;
import com.meet.talk.domain.vo.UserInfoVo;
import com.meet.talk.domain.vo.UserLoginVo;
import com.meet.talk.mapper.ArticleMapper;
import com.meet.talk.mapper.AttentionMapper;
import com.meet.talk.mapper.UserMapper;
import com.meet.talk.service.UserService;
import com.meet.talk.utils.BeanCopyUtils;
import com.meet.talk.utils.JwtUtil;
import com.meet.talk.utils.RedisCache;
import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * (User)表服务实现类
 *
 * @author makejava
 * @since 2022-07-27 10:47:25
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private UserMapper userMapper;
    @Resource
    private ArticleMapper articleMapper;
    @Resource
    private AttentionMapper attentionMapper;
    @Resource
    private RedisCache redisCache;
    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseResult login(User user) {
        log.info("login接口:  {}",user);
        userAuthenticate(user);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getName(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        //获取UserId生成Token
        User loginUser= (User) authenticate.getPrincipal();
        String userId = loginUser.getUId().toString();
        /* String jsonString = JSONObject.toJSONString();*/
        String jwt = JwtUtil.createJWT(userId);
        //将user存入到redis中
        redisCache.setCacheObject(SystemConstants.LOGIN_USER_PREFIX +userId,loginUser,SystemConstants.LOGIN_USER_TTL, TimeUnit.MILLISECONDS);
        //把token和userInfo 封装返回
        //把user转换成userInfoVo
        UserLoginVo loginVo=new UserLoginVo(jwt,loginUser);



        return ResponseResult.okResult(loginVo);
    }

    @Override
    public ResponseResult register(User user) {
        userMapper.insert(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult userInfo(Long uId,Long myUId) {

        User user = userMapper.selectById(uId);
        user.setPassword(null);

        LambdaQueryWrapper<Article> articleLambdaQueryWrapper=new LambdaQueryWrapper<>();
        articleLambdaQueryWrapper.eq(Article::getUId,uId);
        List<Article> articles = articleMapper.selectList(articleLambdaQueryWrapper);
        List<ArticleInfoVo> articleInfoVos = BeanCopyUtils.copyBeanList(articles, ArticleInfoVo.class);

        UserInfoVo userInfoVo=new UserInfoVo();
        userInfoVo.setUser(user);
        userInfoVo.setArticleInfoVos(articleInfoVos);
        //该用户是否被关注  uId:查看的用户的主页, myUId:我的uId
        LambdaQueryWrapper<Attention> attentionLambdaQueryWrapper=new LambdaQueryWrapper<>();
        attentionLambdaQueryWrapper
                .eq(Attention::getUId,myUId)
                .eq(Attention::getFollowUId,uId);
        if (attentionMapper.selectCount(attentionLambdaQueryWrapper)>0){
            userInfoVo.setFollow(true);
        }

        return ResponseResult.okResult(userInfoVo);
    }

    @Override
    public ResponseResult userPrivate(Long uId, Long privateMode) {

        User user = userMapper.selectById(uId);
        user.setPrivateMode(privateMode);
        userMapper.updateById(user);
        return ResponseResult.okResult(user);
    }

    private void userAuthenticate(User user) {
        System.out.println("loginuser"+user.toString());
        //对数据进行非空判断
        if (!StringUtils.hasText(user.getName())){
            throw new SyntaxException(AppHttpCodeEnum.USERNAME_NOT_NULL.getMsg());
        }
        if (!StringUtils.hasText(user.getPassword())){
            throw new SyntaxException(AppHttpCodeEnum.PASSWORD_NOT_NULL.getMsg());
        }
        //存在判断
      /*  if (!user.getPassword().equals(user.getCheckPassword())){
            throw new SyntaxException(AppHttpCodeEnum.PASSWORD_NOT_SAME.getMsg());
        }*/
        //安全性判断
        if (!existUserName(user.getName())){
            throw new SyntaxException(AppHttpCodeEnum.USERNAME_NOT_EXIST.getMsg());
        }
        if (!existPassword(user)){
            throw new SyntaxException(AppHttpCodeEnum.PASSWORD_NOT_EXIST.getMsg());
        }

    }
    private boolean existUserName(String userName) {
        LambdaQueryWrapper<User> queryWrapper =new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getName,userName);
        return count(queryWrapper)>0;
    }
    private boolean existPassword(User userVo) {
        LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getName,userVo.getName());
        User user = userMapper.selectOne(queryWrapper);

        return passwordEncoder.matches(userVo.getPassword(),user.getPassword());
    }

}
