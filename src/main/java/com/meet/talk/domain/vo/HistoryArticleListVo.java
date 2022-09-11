package com.meet.talk.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: alyosha
 * @Date: 2022/7/30 9:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoryArticleListVo {
    private Long uId;
    private  List<HistoryArticleVo>  historyArticleVo;
}
