package com.meet.talk.controller;

import com.meet.talk.domain.Favorites;
import com.meet.talk.domain.FavoritesArticle;
import com.meet.talk.domain.ResponseResult;
import com.meet.talk.domain.User;
import com.meet.talk.service.FavoritesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * (Favorites)表控制层
 *
 * @author makejava
 * @since 2022-08-04 17:02:42
 */
@RestController
@Api(tags = "收藏夹功能")
public class FavoritesController {
    /**
     * 服务对象
     */
    @Resource
    private FavoritesService favoritesService;


    @GetMapping("/favorites/{uId}")
    @ApiOperation(value = "根据用户id查询所有收藏夹")
    public ResponseResult userFavorites(@PathVariable("uId")Long uId){
        return favoritesService.userFavorites(uId);
    }

     @PostMapping("/favorites")
    @ApiOperation(value = "用户创建收藏夹")
    public ResponseResult addFavorites(@RequestBody Favorites favorites){
        return favoritesService.addFavorites(favorites);
    }
  @PostMapping("/deleteFavorites/{fId}")
    @ApiOperation(value = "用户删除收藏夹")
    public ResponseResult deleteFavorites(@PathVariable("fId") Long fId){
        return favoritesService.deleteFavorites(fId);
    }
  @PostMapping("/deleteFavoritesArticle/{id}")
    @ApiOperation(value = "用户删除收藏夹内具体的文章")
    public ResponseResult deleteFavoritesArticle(@PathVariable("id") Long id){
        return favoritesService.deleteFavoritesArticle(id);
    }



    @PostMapping("/favorites/{aId}")
    @ApiOperation(value = "将文章根据用户id添加到收藏夹")
    public ResponseResult addUserFavorites(@PathVariable("aId")Long aId,@RequestBody List<Long> fIds){
        return favoritesService.addUserFavorites(aId,fIds);
    }

    @GetMapping("/favoritesArticle/{fId}")
    @ApiOperation(value = "根据收藏夹id查询对应的所有文章")
    public ResponseResult articleFavorites(@PathVariable("fId" )Long fId){
        return favoritesService.articleFavorites(fId);
    }
}

