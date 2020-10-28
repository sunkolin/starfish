package com.starfish.util;

import com.starfish.module.mail.Mail;
import org.junit.Test;

import javax.mail.MessagingException;

import static org.junit.Assert.assertTrue;

public class MailTest {

    @Test
    public void testSend() throws MessagingException {
        boolean result = true;
        try {
            String receiver = "xiaozhi8859@qq.com";
            String subject = "你的快递已揽收，运单号814674262810";
            String content = "你好，你有一个来自【江苏省南京市江宁区汤山】的快递于 2018-06-05 07:28 发出，运单号814674262810。";
            Mail.send(new String[]{receiver}, null, null, subject, content);
        } catch (Exception e) {
            result = false;
        }
        assertTrue(result);
    }

}