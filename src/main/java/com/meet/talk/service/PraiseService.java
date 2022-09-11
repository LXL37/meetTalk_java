package com.meet.talk.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.meet.talk.domain.Praise;
import com.meet.talk.domain.ResponseResult;

import java.util.List;

/**
 * (Praise)表服务接口
 *
 * @author makejava
 * @since 2022-08-05 18:30:08
 */
public interface PraiseService extends IService<Praise> {


    ResponseResult praise(Long aId, Long uId);

    ResponseResult deletePraise(Long aId, Long uId);
}
