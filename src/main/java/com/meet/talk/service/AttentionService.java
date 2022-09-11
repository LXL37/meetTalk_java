package com.meet.talk.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.meet.talk.domain.Attention;
import com.meet.talk.domain.ResponseResult;

import java.util.List;

/**
 * (Attention)表服务接口
 *
 * @author makejava
 * @since 2022-08-06 08:54:03
 */
public interface AttentionService extends IService<Attention> {


    ResponseResult userFollow(Long uId);

    ResponseResult deleteFollow(Long uId, Long followUId);

    ResponseResult userFans(Long uId);

    ResponseResult addFollow(Long uId, Long followUId);

}
