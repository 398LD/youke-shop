package com.kexun;

import com.kexun.utils.EmailUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

import java.io.IOException;

@SpringBootTest
@EnableAsync
public class TestMail {

    @Autowired
    private EmailUtils emailUtils;

    @Test
    public void test() {
        sendEmail();
        System.out.println("邮件发送结束");
//        try {
////            System.in.read();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }


    public void sendEmail() {
        System.out.println(Thread.currentThread().getName());
        emailUtils.sendSimpleMail("1813723783@qq.com", "验证码", "100000");


    }

}
