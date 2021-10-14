package com.starfish.util;

import org.junit.Assert;
import org.junit.Test;

import javax.mail.MessagingException;

public class MailUtilTest {

    @Test
    public void testSend() throws MessagingException {
        String receiver = "xiaozhi8859@qq.com";
        String subject = "海星发送邮件测试";
        String content = "你好，海星发送邮件测试。";
        MailUtil.send(new String[]{receiver}, null, null, subject, content);
    }

}