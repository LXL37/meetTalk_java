package com.meet.talk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.meet.talk.domain.Comment;
import org.apache.ibatis.annotations.Mapper;

/**
 * (Comment)表数据库访问层
 *
 * @author makejava
 * @since 2022-07-27 10:44:42
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

}
