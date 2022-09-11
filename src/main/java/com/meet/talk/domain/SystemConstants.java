package com.meet.talk.domain;

/**
 * @Author: alyosha
 * @Date: 2022/7/30 19:43
 */
public class SystemConstants {


    /**
     * 历史浏览的记录数
     */
    public static final int HISTORY_ARTICLE_MAX_NUM=5;

    public static final Long LOGIC_DELETE=1L;

    public static final Long LOGIC_NOT_DELETE=0L;
    /**
     * 用户登录的前缀
     */
    public static final String LOGIN_USER_PREFIX ="meetLogin" ;
    /**
     * 用户登录的缓存  1h
     */
    public static final Long LOGIN_USER_TTL=60 *  60 * 1000L;
    /**
     * 历史浏览文章缓存 48h
     */
    public static final Long HISTORY_ARTICLE_TTL=60*60*48*1000L;
}
