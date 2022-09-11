package com.meet.talk.controller;

import com.meet.talk.domain.Praise;
import com.meet.talk.domain.ResponseResult;
import com.meet.talk.service.PraiseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import javax.annotation.Resource;

/**
 * (Praise)表控制层
 *
 * @author makejava
 * @since 2022-08-05 18:30:08
 */
@RestController
@Api(tags = "点赞功能")
public class PraiseController {
    /**
     * 服务对象
     */
    @Resource
    private PraiseService praiseService;


    @PostMapping("/praise/{aId}/{uId}")
    @ApiOperation(value = "用户点赞,新增数据")
    public ResponseResult praise(@PathVariable("aId")Long aId,@PathVariable("uId") Long uId){
        return praiseService.praise(aId,uId);

    }
    @PostMapping("/deletePraise/{aId}/{uId}")
    @ApiOperation(value = "取消点赞,删除数据")
    public ResponseResult deletePraise(@PathVariable("aId")Long aId,@PathVariable("uId") Long uId){
        return praiseService.deletePraise(aId,uId);

    }
}

