package com.starfish.common.permission;

import com.starfish.common.push.pushdeer.PushDeerImpl;
import com.starfish.common.push.pushdeer.PushDeerParam;
import com.starfish.common.push.pushdeer.PushDeerProperties;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * PushDeerTest
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2026-01-06
 */
@Slf4j
public class PushDeerTest {

    public static final String pushKey = "PDU32883T0Odg11tOI7DT366uZztGMY4INwV967j7";

    @Test
    void messagePush() {
        PushDeerParam param = new PushDeerParam();
        param.setPushKey(pushKey);
        param.setText("你好，正在测试PushDeer");
        PushDeerProperties pushDeerProperties = new PushDeerProperties();
        PushDeerImpl pushDeer = new PushDeerImpl(pushDeerProperties);
        pushDeer.push(param);
    }

}
