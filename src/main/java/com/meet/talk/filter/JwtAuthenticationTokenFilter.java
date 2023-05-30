package com.meet.talk.filter;

import com.alibaba.fastjson.JSON;

import com.meet.talk.domain.AppHttpCodeEnum;
import com.meet.talk.domain.ResponseResult;
import com.meet.talk.domain.SystemConstants;
import com.meet.talk.domain.User;
import com.meet.talk.domain.vo.UserLoginVo;
import com.meet.talk.utils.JwtUtil;

import com.meet.talk.utils.RedisCache;
import com.meet.talk.utils.WebUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @Author: alyosha
 * @Date: 2022/3/25 10:51
 */
@Component
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Resource
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        //获取请求头中的token
        String token = request.getHeader("token");

        if (!StringUtils.hasText(token)) {
            //说明该接口不需要登录  直接放行
            filterChain.doFilter(request, response);
            return;
        }
        //解析获取userid
        Claims claims = null;
        try {
            claims = JwtUtil.parseJWT(token);
        } catch (Exception e) {

            e.printStackTrace();
            //token超时  token非法
            //响应告诉前端需要重新登录
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_RE_LOGIN);
            WebUtils.renderString(response, JSON.toJSONString(result));
            return;
        }
        String userId = claims.getSubject();

        //todo 从缓存中获取用户信息

        //从redis中获取用户信息
        UserLoginVo loginUser = redisCache.getCacheObject(SystemConstants.LOGIN_USER_PREFIX + userId);
        //如果获取不到
        if (Objects.isNull(loginUser)) {
            //说明登录过期  提示重新登录
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_RE_LOGIN);
            WebUtils.renderString(response, JSON.toJSONString(result));

            return;
        }

        //token 不相同
        if (!Objects.equals(loginUser.getToken(), token)) {
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response, JSON.toJSONString(result));
            return;
        }

        //存入SecurityContextHolder
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getUser().getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);


        filterChain.doFilter(request, response);

    }


}