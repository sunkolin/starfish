package com.starfish.util;

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
        assertTrue(new File(filePath).exists());
    }

    @Test
    public void getAddress() {
        String result = WebUtil.getAddress("203.119.241.126");
        assertEquals("中国 广东 深圳",result);
    }

    @Test
    public void existMedia() {
        String url = "https://vd3.bdstatic.com/mda-mj84pad7qad8hhur/sc/cae_h264_clips/1633750377008601665/mda-mj84pad7qad8hhur.mp4";
        assertTrue(WebUtil.existMedia(url));
    }

}
