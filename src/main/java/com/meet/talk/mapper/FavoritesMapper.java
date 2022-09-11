package com.meet.talk.mapper;

import com.meet.talk.domain.Favorites;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * (Favorites)表数据库访问层
 *
 * @author makejava
 * @since 2022-08-04 17:02:44
 */
@Mapper
public interface FavoritesMapper extends BaseMapper<Favorites> {

}
