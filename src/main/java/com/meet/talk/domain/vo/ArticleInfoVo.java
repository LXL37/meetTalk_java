package com.meet.talk.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: alyosha
 * @Date: 2022/8/4 9:56
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleInfoVo {
    private Long aId;
    private String title;
    private Long uId;
    private Date createTime;


}
