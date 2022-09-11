package com.meet.talk.domain;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (FavoritesArticle)实体类
 *
 * @author makejava
 * @since 2022-08-04 17:26:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavoritesArticle implements Serializable {
    private static final long serialVersionUID = -43376413968604074L;
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long fId;

    private Long aId;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
}
