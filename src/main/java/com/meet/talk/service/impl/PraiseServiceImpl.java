package com.meet.talk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.meet.talk.domain.Praise;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meet.talk.domain.ResponseResult;
import com.meet.talk.mapper.PraiseMapper;
import com.meet.talk.service.PraiseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * (Praise)表服务实现类
 *
 * @author makejava
 * @since 2022-08-05 18:30:08
 */
@Service
public class PraiseServiceImpl extends ServiceImpl<PraiseMapper, Praise> implements PraiseService {

    @Resource
    private PraiseMapper praiseMapper;


    @Override
    public ResponseResult praise(Long aId, Long uId) {
        Praise praise=new Praise();
        praise.setAId(aId);
        praise.setUId(uId);
        praiseMapper.insert(praise);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deletePraise(Long aId, Long uId) {
        LambdaQueryWrapper<Praise> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper
                .eq(Praise::getAId,aId)
                .eq(Praise::getUId,uId);
        praiseMapper.delete(queryWrapper);
        return ResponseResult.okResult();
    }
}
