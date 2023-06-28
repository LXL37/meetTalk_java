package com.meet.talk.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class mybatisPlusHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createTime", new Date(System.currentTimeMillis()+Long.parseLong("28800000")), metaObject);

    }

    @Override
    public void updateFill(MetaObject metaObject) {

    }
}
