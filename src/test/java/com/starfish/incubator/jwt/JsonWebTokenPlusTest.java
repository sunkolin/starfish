package com.starfish.incubator.jwt;

import com.starfish.core.context.User;
import com.starfish.core.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * JsonWebTokenPlusTest
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2022-07-26
 */
@Slf4j
class JsonWebTokenPlusTest {

    @Test
    void test() {
        User user = new User();
        user.setUserId(100L);

        String token = JsonWebTokenPlus.create(user);
        log.info(token);

        boolean verify = JsonWebTokenPlus.verify(token);
        log.info("{}",verify);

        User user1 = JsonWebTokenPlus.verify(token, User.class);
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

        String token = JsonWebTokenPlus.create(userId, user);
        log.info(token);

        boolean verify = JsonWebTokenPlus.verify(userId, token);
        log.info("{}",verify);

        User user1 = JsonWebTokenPlus.verify(userId, token, User.class);
        log.info(JsonUtil.toJson(user1));

        long expectedValue = 100L;
        long actualValue = user1.getUserId();
        Assertions.assertEquals(expectedValue, actualValue);
    }

}
