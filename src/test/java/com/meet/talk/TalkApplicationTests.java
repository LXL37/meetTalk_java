package com.meet.talk;

import com.meet.talk.domain.Comment;
import com.meet.talk.mapper.CommentMapper;
import com.meet.talk.testUser.TestUser;
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



        /*
        （1）甲使用对称密钥对明文进行加密，生成密文信息。
（2）甲使用乙的公钥加密对称密钥，生成数字信封。
（3）甲将数字信封和密文信息一起发送给乙。
（4）乙接收到甲的加密信息后，使用自己的私钥打开数字信封，得到对称密钥。
（5）乙使用对称密钥对密文信息进行解密，得到最初的明文。


        * */

        TestUser testUser=new TestUser("a");



    }

}
