package com.meet.talk.domain;

/**
 * @author lenovo
 */

public enum AppHttpCodeEnum {
    // 成功
    SUCCESS(200,"操作成功"),

    // 登录
    NEED_LOGIN(401,"需要登录后操作"),
    NEED_RE_LOGIN(402,"请重新登录!"),

    NO_OPERATOR_AUTH(403,"无权限操作"),
    ERROR(500,"出现错误"),
    FAVORITES_ERROR(500,"已全部收藏!"),
    FAVORITES_NAME_NOT_NULL(500,"收藏夹名字不能为空!"),

     PHONENUMBER_EXIST(502,"手机号已存在"), EMAIL_EXIST(503, "邮箱已存在"),

    CONTENT_NOT_NULL(506, "评论不能为空"),
    FILE_TYPE_ERROR(507, "文件类型错误"),
    //user表
    REQUIRE_USERNAME(504, "必需填写用户名"),
    USERNAME_NOT_NULL(508, "用户名不能为空"),
    USERNAME_EXIST(501,"用户名已存在"),
    USERNAME_NOT_EXIST(512, "用户名不存在"),
    NICKNAME_NOT_NULL(509, "昵称不能为空"),
    EMAIL_NOT_NULL(511, "邮箱不能为空"),
    PHONE_NOT_NULL(513, "手机号不能为空"),
    PHONE_EXIST(513, "手机号已存在"),
    PASSWORD_NOT_NULL(510, "密码不能为空"),
    PASSWORD_NOT_SAME(514,"两次密码不一样"),
    PASSWORD_NOT_EXIST(514,"密码错误"),
    PASSWORD_NOT_SAFE(514,"密码不符合要求"),



    //权限表:

    ROLE_NAME_NOT_NULL(514,"角色名称不能为空"),
    ROLE_NAME_EXIST(514,"角色名称已经存在"),
    CODE_EXIST(514,"唯一编码存在"),
    ROLE_CODE_NOT_NULL(514,"唯一编码不能为空"),


    LOGIN_ERROR(515,"用户名或密码错误"),
    ARTICLE_DELETED(516,"该文章已被删除"),
    STATUS_NOT_NULL(514,"请选择状态码"), SYSTEM_ERROR(520,"出现错误!" );



    int code;
    String msg;

    AppHttpCodeEnum(int code, String errorMessage){
        this.code = code;
        this.msg = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }


}
