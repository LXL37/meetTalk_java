package com.meet.talk.domain.vo;

import com.meet.talk.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: alyosha
 * @Date: 2022/9/4 9:47
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginVo {
   private String token;
   private User user;


}
