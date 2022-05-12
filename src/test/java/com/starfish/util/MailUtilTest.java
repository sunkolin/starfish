package com.starfish.util;

import com.google.common.collect.Lists;
import org.junit.Test;

import javax.mail.MessagingException;

public class MailUtilTest {

    @Test
    public void testSend() throws MessagingException {
        String receiver = "jinkela008@foxmail.com";
        String subject = "邮件系统";
        String content = "你好，此邮件由邮件系统发送。";
        MailUtil.send(Lists.newArrayList(receiver), null, null, subject, content);
    }

}