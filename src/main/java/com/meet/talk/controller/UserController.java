package com.meet.talk.controller;

import com.meet.talk.domain.ResponseResult;
import com.meet.talk.domain.User;
import com.meet.talk.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * (User)表控制层
 *
 * @author makejava
 * @since 2022-07-27 10:47:24
 */
@RestController
@Api(tags = "用户接口")
public class UserController {
    /**
     * 服务对象
     */
    @Resource
    private UserService userService;

    @PostMapping("/login")
    @ApiOperation(value = "登录")
    public ResponseResult login(@RequestBody @Valid User user){
        return  userService.login(user);
    }

    @PostMapping("/register")
    @ApiOperation(value="注册用户")
    public ResponseResult register(@RequestBody @Valid User user){return  userService.register(user);}

    @GetMapping("/user/{uId}/{myUId}")
    @ApiOperation(value = "查询用户的个人信息主页")
    public ResponseResult userInfo(@PathVariable("uId") Long uId,@PathVariable("myUId") Long myUId){
        return  userService.userInfo(uId,myUId);
    }
    @PostMapping("/private/{uId}/{privateMode}")
    @ApiOperation(value = "修改用户私密模式")
    public ResponseResult userPrivate(@PathVariable("uId") Long uId,@PathVariable("privateMode") Long privateMode){
        return  userService.userPrivate(uId,privateMode);
    }
}

