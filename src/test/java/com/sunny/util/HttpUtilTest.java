package com.sunny.util;

import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * HttpUtilTest
 *
 * @author sunny
 * @version 1.0.0
 * @since 2015-03-23
 */
public class HttpUtilTest {

    private String workspace = "";

    @Before
    public void before() {
        workspace = System.getProperty("user.dir") + "/target/image/";
    }

    @Test
    public void downloadTest() {
        String filePath = workspace + System.currentTimeMillis() + "baidu.ico";
        HttpUtil.download("http://www.baidu.com/favicon.ico", filePath);
        assertTrue(new File(filePath).exists());
    }

    @Test
    public void getIpLocation() {
        String result = HttpUtil.getLocation("61.135.169.121");
        assertEquals("中国##北京##北京",result);
    }

    @Test
    public void existMedia() {
        String url = "http://baidudown-al.wasu.cn/pcsan12/mams/vod/201708/07/18/20170807183604432b759dbfe.mp4";
        assertTrue(HttpUtil.existMedia(url));
    }

}
