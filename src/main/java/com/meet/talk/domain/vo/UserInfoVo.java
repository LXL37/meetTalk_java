package com.meet.talk.domain.vo;

import com.meet.talk.domain.Article;
import com.meet.talk.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: alyosha
 * @Date: 2022/8/4 9:42
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoVo {
    private User user;
    private boolean isFollow;
    private List<ArticleInfoVo> articleInfoVos ;
}
