package com.starfish.core.util;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MailUtilTest {

    @Test
    void testSend() {
        String receiver = "jinkela2012@tom.com";
        String subject = "starfish";
        String content = "你好，此邮件由starfish发送。";
        Assertions.assertDoesNotThrow(() -> MailUtil.send(Lists.newArrayList(receiver), null, null, subject, content));
    }

}