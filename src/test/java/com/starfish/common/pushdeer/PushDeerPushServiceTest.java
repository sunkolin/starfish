package com.starfish.common.pushdeer;

import com.starfish.common.push.pushdeer.PushDeerPushService;
import com.starfish.common.push.pushdeer.PushDeerRequest;
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
@Deprecated
public class PushDeerPushServiceTest {

    public static final String pushKey = "PDU32883T0Odg11tOI7DT366uZztGMY4INwV967j7";

    @Test
    void messagePush() {
        PushDeerRequest param = new PushDeerRequest();
        param.setPushKey(pushKey);
        param.setText("你好，正在测试PushDeer");
        PushDeerProperties pushDeerProperties = new PushDeerProperties();
        PushDeerPushService pushDeerPushService = new PushDeerPushService(pushDeerProperties);
        pushDeerPushService.push(param);
    }

}
