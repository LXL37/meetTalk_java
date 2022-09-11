package com.meet.talk.domain;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * (Favorites)实体类
 *
 * @author makejava
 * @since 2022-08-04 17:02:44
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Favorites implements Serializable {
    private static final long serialVersionUID = 447983689045445488L;

    @TableId(type = IdType.AUTO)
    private Long fId;
    @NotBlank(message = "收藏夹名字不能为空!")
    private String fName;

    private Long uId;




}
