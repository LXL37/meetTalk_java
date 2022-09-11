package com.meet.talk.mapper;

import com.meet.talk.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * (User)表数据库访问层
 *
 * @author makejava
 * @since 2022-07-27 10:47:25
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
