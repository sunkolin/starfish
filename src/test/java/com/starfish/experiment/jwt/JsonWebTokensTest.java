package com.starfish.experiment.jwt;

import com.starfish.core.context.User;
import com.starfish.core.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * JsonWebTokenTest
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2022-07-26
 */
@Slf4j
class JsonWebTokensTest {

    @Test
    void test() {
        User user = new User();
        user.setUserId(100L);

        String token = JsonWebTokens.create(user);
        log.info(token);

        boolean verify = JsonWebTokens.verify(token);
        log.info("{}",verify);

        User user1 = JsonWebTokens.verify(token, User.class);
        log.info(JsonUtil.toJson(user1));

        long expectedValue = 100L;
        long actualValue = user1.getUserId();
        Assertions.assertEquals(expectedValue, actualValue);
    }

    @Test
    void test2() {
        User user = new User();
        Long userId = 100L;
        user.setUserId(userId);

        String token = JsonWebTokens.create(userId, user);
        log.info(token);

        boolean verify = JsonWebTokens.verify(userId, token);
        log.info("{}",verify);

        User user1 = JsonWebTokens.verify(userId, token, User.class);
        log.info(JsonUtil.toJson(user1));

        long expectedValue = 100L;
        long actualValue = user1.getUserId();
        Assertions.assertEquals(expectedValue, actualValue);
    }

}
