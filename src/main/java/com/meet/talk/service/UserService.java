package com.meet.talk.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.meet.talk.domain.ResponseResult;
import com.meet.talk.domain.User;

import java.util.List;

/**
 * (User)表服务接口
 *
 * @author makejava
 * @since 2022-07-27 10:47:24
 */
public interface UserService extends IService<User> {


    ResponseResult login(User user);

    ResponseResult register(User user);

    ResponseResult userInfo(Long uId,Long myUId);

    ResponseResult userPrivate(Long uId, Long privateMode);

    ResponseResult updateUser(User user);
}
