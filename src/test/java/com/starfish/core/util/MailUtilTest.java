package com.starfish.core.util;

import com.google.common.collect.Lists;
import org.junit.Test;

import javax.mail.MessagingException;

public class MailUtilTest {

    @Test
    public void testSend() throws MessagingException {
        String receiver = "jinkela2012@tom.com";
        String subject = "starfish";
        String content = "你好，此邮件由starfish发送。";
        MailUtil.send(Lists.newArrayList(receiver), null, null, subject, content);
    }

}