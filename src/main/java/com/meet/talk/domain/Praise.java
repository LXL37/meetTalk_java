package com.meet.talk.domain;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (Praise)实体类
 *
 * @author makejava
 * @since 2022-08-05 18:30:09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Praise implements Serializable {
    private static final long serialVersionUID = 906228706597247654L;
    @TableId(type = IdType.AUTO)
    private Long pId;

    private Long aId;

    private Long uId;



}
