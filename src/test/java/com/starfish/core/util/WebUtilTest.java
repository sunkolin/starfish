package com.starfish.core.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;

/**
 * WebUtilTest
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2015-03-23
 */
@Slf4j
class WebUtilTest {

    private static String workspace = "";

    @BeforeAll
    public static void before() {
        workspace = System.getProperty("user.dir") + "/target/image/";
    }

    @Test
    void downloadTest() {
        String filePath = workspace + System.currentTimeMillis() + ".ico";
        WebUtil.download("http://www.baidu.com/favicon.ico", filePath);
        Assertions.assertTrue(new File(filePath).exists());
    }

    @Test
    void existMedia() {
        String url = "https://vd3.bdstatic.com/mda-mj84pad7qad8hhur/sc/cae_h264_clips/1633750377008601665/mda-mj84pad7qad8hhur.mp4";
        Assertions.assertTrue(WebUtil.existMedia(url));
    }

    @Test
    void webTest() {
        log.info(WebUtil.getBaseUrl("http://new.dongying.gov.cn/art/2018/8/31/art_43576_3069835.html"));
        log.info(WebUtil.getBaseUrl("https://10.10.20.30:8080/art/2018/8/31/art_43576_3069835.html"));

        log.info(WebUtil.getScheme("http://new.dongying.gov.cn/art/2018/8/31/art_43576_3069835.html"));
        log.info(WebUtil.getScheme("https://10.10.20.30:8080/art/2018/8/31/art_43576_3069835.html"));

        log.info(WebUtil.getHost("http://new.dongying.gov.cn/art/2018/8/31/art_43576_3069835.html"));
        log.info(WebUtil.getHost("http://10.10.20.30:8080/art/2018/8/31/art_43576_3069835.html"));

        String port1 =  WebUtil.getPort("http://new.dongying.gov.cn/art/2018/8/31/art_43576_3069835.html");
        log.info(port1);
        Assertions.assertEquals("80", port1);
        log.info(WebUtil.getPort("http://10.10.20.30:8080/art/2018/8/31/art_43576_3069835.html"));

    }

}
