package com.starfish.common.bark;

import com.starfish.common.push.bark.Bark;
import com.starfish.common.push.bark.BarkParam;
import com.starfish.common.push.bark.BarkProperties;
import com.starfish.common.push.pushdeer.PushDeer;
import com.starfish.common.push.pushdeer.PushDeerParam;
import com.starfish.common.push.pushdeer.PushDeerProperties;
import com.starfish.core.util.JsonUtil;
import com.starfish.core.util.RestTemplates;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

/**
 * BarkTest
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2026-01-06
 */
@Slf4j
public class BarkTest {

    public static final String DEVICE_KEY = "JzsnpBXdzX7Xd5jiWZLKL";

    @Disabled("")
    @Test
    void messagePush() {
        BarkParam param = new BarkParam();
        param.setTitle("Bark 通知");
        param.setBody("你好，正在测试Bark");
        param.setDevice_key(DEVICE_KEY);
        BarkProperties barkProperties = new BarkProperties();
        Bark bark = new Bark(barkProperties);
        bark.push(param);
    }

}
