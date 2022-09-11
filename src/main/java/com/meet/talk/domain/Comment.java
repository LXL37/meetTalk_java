package com.meet.talk.domain;

import java.util.Date;
import java.io.Serializable;
import java.util.List;

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

/**
 * (Comment)实体类
 *
 * @author makejava
 * @since 2022-07-27 19:33:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment implements Serializable {
    private static final long serialVersionUID = -38016540608523506L;
    @TableId(type = IdType.AUTO)
    private Long cId;


    private Long uId;


    private Long aId;
    @NotBlank(message = "评论内容不能为空!")
    private String comment;
    @TableField(exist = false)
    private String toName;
    @TableField(exist = false)
    private String name;
    @TableField(exist = false)
    private String avatar;

    private Integer root;

    private Long rootCId;


    private Long parentUId;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(exist = false)
    private List<Comment> childComments;


}
