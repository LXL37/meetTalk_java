package com.meet.talk;

import com.meet.talk.domain.Comment;
import com.meet.talk.mapper.CommentMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;

@SpringBootTest
class TalkApplicationTests {
    @Resource
    private CommentMapper commentMapper;
    @Test
    void contextLoads() {

        BCryptPasswordEncoder a=new BCryptPasswordEncoder();
      notifyAll();
        System.out.println(a.encode("1234"));
    }

}
