package com.meet.talk.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.meet.talk.domain.Favorites;
import com.meet.talk.domain.ResponseResult;

import java.util.List;

/**
 * (Favorites)表服务接口
 *
 * @author makejava
 * @since 2022-08-04 17:02:43
 */
public interface FavoritesService extends IService<Favorites> {


    ResponseResult userFavorites(Long uId);

    ResponseResult articleFavorites(Long fId);

    ResponseResult addUserFavorites(Long aId, List<Long> fIds);

    ResponseResult addFavorites(Favorites favorites);

    ResponseResult deleteFavorites(Long fId);

    ResponseResult deleteFavoritesArticle(Long id);
}
