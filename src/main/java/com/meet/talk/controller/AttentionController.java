package com.meet.talk.controller;

import com.meet.talk.domain.Attention;
import com.meet.talk.domain.ResponseResult;
import com.meet.talk.service.AttentionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import javax.annotation.Resource;

/**
 * (Attention)表控制层
 *
 * @author makejava
 * @since 2022-08-06 08:54:02
 */
@RestController
@Api(tags = "关注功能")
public class AttentionController {
    /**
     * 服务对象
     */
    @Resource
    private AttentionService attentionService;


    @GetMapping("/follow/{uId}")
    @ApiOperation(value = "用户的关注")
    public ResponseResult userFollow(@PathVariable("uId") Long uId){
        return attentionService.userFollow(uId);
    }
    @GetMapping("/fans/{uId}")
    @ApiOperation(value = "用户的粉丝")
    public ResponseResult userFans(@PathVariable("uId") Long uId){
        return attentionService.userFans(uId);
    }
    @DeleteMapping("/follow/{uId}/{followUId}")
    @ApiOperation(value = "用户取消关注")
    public ResponseResult deleteFollow(@PathVariable("uId") Long uId,@PathVariable("followUId") Long followUId){
        return attentionService.deleteFollow(uId,followUId);
    }
    @PostMapping("/follow/{uId}/{followUId}")
    @ApiOperation(value = "用户关注")
    public ResponseResult addFollow(@PathVariable("uId") Long uId,@PathVariable("followUId") Long followUId){
        return attentionService.addFollow(uId,followUId);
    }

}

