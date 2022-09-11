package com.meet.talk.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;

/**
 * @Author: alyosha
 * @Date: 2022/7/29 21:22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoryArticleVo {
    private Long uId;
    private Long aId;
    private String title;
    private String avatar;
    private Long clickTime;
}
