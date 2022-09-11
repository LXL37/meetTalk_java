package com.meet.talk.domain;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (Attention)实体类
 *
 * @author makejava
 * @since 2022-08-06 08:54:03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Attention implements Serializable {
    private static final long serialVersionUID = 197134070122762493L;
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 用户关注的Id
     */
    private Long followUId;
    /**
     * 用户id
     */
    private Long uId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
