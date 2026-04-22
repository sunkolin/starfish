package com.starfish.common.bark;

import com.starfish.common.push.bark.BarkPushService;
import com.starfish.common.push.bark.BarkRequest;
import com.starfish.common.push.bark.BarkProperties;
import com.starfish.core.util.DateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Date;

/**
 * BarkTest
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2026-01-06
 */
@Slf4j
public class BarkPushServiceTest {

    public static final String OFFICIAL_CHANNEL_DEVICE_KEY = "JzsnpBXdzX7Xd5jiWZLKL";

    public static final String OWN_CHANNEL_DEVICE_KEY = "NxUUkyn3avVJtWq7aHNZMC";

    @Test
    void pushByOfficialChannel() {
        BarkRequest param = new BarkRequest();
        param.setTitle("Bark");
        param.setBody("你好，正在测试Bark，现在时间是：" + DateTimeUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
        param.setDevice_key(OFFICIAL_CHANNEL_DEVICE_KEY);
        BarkProperties barkProperties = new BarkProperties();
        BarkPushService barkPushService = new BarkPushService(barkProperties);
        barkPushService.push(param);
    }

    @Test
    void pushByOwnChannel() {
        BarkRequest param = new BarkRequest();
        param.setTitle("Bark");
        param.setBody("你好，正在测试Bark，现在时间是：" + DateTimeUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
        param.setDevice_key(OWN_CHANNEL_DEVICE_KEY);
        BarkProperties barkProperties = new BarkProperties();
        barkProperties.setBaseUrl("http://sunkolin.xyz:28080");
        BarkPushService barkPushService = new BarkPushService(barkProperties);
        barkPushService.push(param);
    }

}
