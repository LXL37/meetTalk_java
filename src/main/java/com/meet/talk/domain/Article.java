package com.meet.talk.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * (Article)实体类
 *
 * @author makejava
 * @since 2022-07-27 10:32:06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article implements Serializable {
    private static final long serialVersionUID = 685217095417493753L;
    @TableId(type = IdType.AUTO)
    private Long aId;


    private Long uId;
    @NotBlank(message = "标题不能为空!")
    @Size(max=32,message = "标题字数不能超过32个字!")
    private String title;
    @NotBlank(message = "简介不能为空!")
    @Size(max=32,message="简介字数不能超过32个字!")
    private String summary;
    @NotBlank(message = "内容不能为空!")
    private String content;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(exist = false)
    private String name;
    @TableField(exist = false)
    private String avatar;

    @TableField(exist = false)
    private Long commentNum;
    @TableField(exist = false)
    private boolean isFavorites;
    /**
     * favoriteArticle表的ID 唯一标识
     */
    @TableField(exist = false)
    private Long favoriteArticleId;

    /**
     * 标识该用户是否点赞
     */
    @TableField(exist = false)
    private boolean isPraise;
    @TableField(exist = false)
    private Long praiseNum;

}
