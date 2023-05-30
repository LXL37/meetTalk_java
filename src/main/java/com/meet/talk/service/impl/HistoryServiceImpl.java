package com.meet.talk.service.impl;

import com.meet.talk.domain.SystemConstants;
import com.meet.talk.domain.ResponseResult;
import com.meet.talk.domain.vo.HistoryArticleListVo;
import com.meet.talk.domain.vo.HistoryArticleVo;
import com.meet.talk.mapper.ArticleMapper;
import com.meet.talk.service.HistoryService;
import com.meet.talk.utils.RedisCache;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Author: alyosha
 * @Date: 2022/7/29 21:25
 */
@Service
public class HistoryServiceImpl implements HistoryService {
    @Resource
    private RedisCache redisCache;

    @Resource
    private ArticleMapper articleMapper;

    @Override
    public ResponseResult historyArticle(HistoryArticleVo historyArticleVo) {

        //对应 用户 还没有缓存 或者已经过期
        if (redisCache.getCacheObject((String.valueOf(historyArticleVo.getUId()))) == null) {
            HistoryArticleListVo historyArticleListVo = new HistoryArticleListVo();
            List<HistoryArticleVo> articleVos = new ArrayList<>();
            articleVos.add(historyArticleVo);
            historyArticleListVo.setHistoryArticleVo(articleVos);

            //只缓存一天
            redisCache.setCacheObject(String.valueOf(historyArticleVo.getUId()), historyArticleListVo, SystemConstants.HISTORY_ARTICLE_TTL, TimeUnit.MILLISECONDS);
        } else {


            boolean flag = false;
            HistoryArticleListVo articleListVo = redisCache.getCacheObject(String.valueOf(historyArticleVo.getUId()));
            List<HistoryArticleVo> articleVos = articleListVo.getHistoryArticleVo();
            for (HistoryArticleVo articleVo : articleVos) {
                // 判断是否重复
                if (articleVo.getAId().equals(historyArticleVo.getAId())) {
                    flag = true;
                    //更新时间戳
                    articleVo.setClickTime(historyArticleVo.getClickTime());
                    break;
                }
            }
            if (flag) {
                //更新时间戳

                redisCache.setCacheObject(String.valueOf(historyArticleVo.getUId()), articleListVo, SystemConstants.HISTORY_ARTICLE_TTL, TimeUnit.MILLISECONDS);
                ResponseResult.okResult();
            } else {
                articleVos.add(historyArticleVo);
                redisCache.setCacheObject(String.valueOf(historyArticleVo.getUId()), articleListVo, SystemConstants.HISTORY_ARTICLE_TTL, TimeUnit.MILLISECONDS);
            }


        }


        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult historyArticleList(Long uId) {

        HistoryArticleListVo articleListVo = null;
        List<HistoryArticleVo> articleVos = new ArrayList<>();

        try {
            articleListVo = redisCache.getCacheObject((String.valueOf(uId)));
            List<HistoryArticleVo> historyArticleVo = articleListVo.getHistoryArticleVo();
            //只返回最近点击的5条记录
            historyArticleVo.stream()
                    .sorted((o1, o2) -> (int) (o2.getClickTime() - o1.getClickTime()))
                    .forEach(historyArticleVo1 -> {
                        if (articleVos.size() < SystemConstants.HISTORY_ARTICLE_MAX_NUM) {
                            articleVos.add(historyArticleVo1);
                        }

                    });
        } catch (Exception e) {
            return ResponseResult.okResult();
        }


        return ResponseResult.okResult(articleVos);
    }
}
