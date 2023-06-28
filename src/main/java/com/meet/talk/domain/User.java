package com.meet.talk.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import com.meet.talk.validation.UserLogin;
import com.meet.talk.validation.UserRegister;
import com.meet.talk.validation.group.UserRegisterGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Date;
import java.io.Serializable;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * (User)实体类
 *
 * @author makejava
 * @since 2022-07-27 10:47:25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
//@UserLogin
@UserRegister(groups = UserRegisterGroup.class)
public class User implements Serializable, UserDetails, Annotation {
    private static final long serialVersionUID = 971874414705469084L;
    @TableId(type = IdType.AUTO)
    private Long uId;
    @NotBlank(message = "用户名不能为空!")
    @Pattern(regexp = "^([\\u4e00-\\u9fa5]|[\\w]){2,8}$",message = "用户名请输入2-8位字符,不能包含特殊字符！",groups = UserRegisterGroup.class)
    private String name;

    private String avatar;
    @NotBlank(message = "密码不能为空!")
    @Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,10}$",message = "密码请输入6-10位必须包含字母数字,不能包含特殊字符！",groups = UserRegisterGroup.class)
    private String password;
    @Size(min = 0,max = 16)
    private String signature;
    @TableField(fill = FieldFill.INSERT)

    private Date createTime;
    private Long privateMode;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {

        return this.name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }
}
