package com.meet.talk.service;

import com.meet.talk.domain.ResponseResult;
import com.meet.talk.domain.vo.HistoryArticleVo;

/**
 * @Author: alyosha
 * @Date: 2022/7/29 21:25
 */
public interface HistoryService {
    ResponseResult historyArticle(HistoryArticleVo historyArticleVo);

    ResponseResult historyArticleList(Long uId);
}
