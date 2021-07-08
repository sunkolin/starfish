package com.starfish;

import com.starfish.core.util.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * WeatherPlusTest
 *
 * @author sunny
 * @version 1.0.0
 * @since 2021-03-10
 */
@Slf4j
public class WebUtilTest {

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
