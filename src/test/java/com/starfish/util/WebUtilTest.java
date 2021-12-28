package com.starfish.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

/**
 * WebUtilTest
 *
 * @author neacle
 * @version 1.0.0
 * @since 2015-03-23
 */
@Slf4j
public class WebUtilTest {

    private String workspace = "";

    @Before
    public void before() {
        workspace = System.getProperty("user.dir") + "/target/image/";
    }

    @Test
    public void downloadTest() {
        String filePath = workspace + System.currentTimeMillis() + ".ico";
        WebUtil.download("http://www.baidu.com/favicon.ico", filePath);
        Assert.assertTrue(new File(filePath).exists());
    }

    @Test
    public void getAddress() {
        String result = WebUtil.getAddress("203.119.241.126");
        Assert.assertEquals("中国 广东 深圳", result);
    }

    @Test
    public void existMedia() {
        String url = "https://vd3.bdstatic.com/mda-mj84pad7qad8hhur/sc/cae_h264_clips/1633750377008601665/mda-mj84pad7qad8hhur.mp4";
        Assert.assertTrue(WebUtil.existMedia(url));
    }

    @Test
    public void weatherPlusTest() {
        log.info(WebUtil.getBaseUrl("http://new.dongying.gov.cn/art/2018/8/31/art_43576_3069835.html"));
        log.info(WebUtil.getBaseUrl("https://10.10.20.30:8080/art/2018/8/31/art_43576_3069835.html"));

        log.info(WebUtil.getScheme("http://new.dongying.gov.cn/art/2018/8/31/art_43576_3069835.html"));
        log.info(WebUtil.getScheme("https://10.10.20.30:8080/art/2018/8/31/art_43576_3069835.html"));

        log.info(WebUtil.getHost("http://new.dongying.gov.cn/art/2018/8/31/art_43576_3069835.html"));
        log.info(WebUtil.getHost("http://10.10.20.30:8080/art/2018/8/31/art_43576_3069835.html"));

        log.info(WebUtil.getPort("http://new.dongying.gov.cn/art/2018/8/31/art_43576_3069835.html"));
        log.info(WebUtil.getPort("http://10.10.20.30:8080/art/2018/8/31/art_43576_3069835.html"));
    }

}
