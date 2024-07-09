package com.starfish.incubator.jwt;

import com.starfish.core.context.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * JsonWebTokenPlusTest
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2022-07-26
 */
class JsonWebTokenPlusTest {

    @Test
    void test() {
        User user = new User();
        user.setUserId(100L);

        String token = JsonWebTokenPlus.create(user);
        System.out.println(token);

        boolean verify = JsonWebTokenPlus.verify(token);
        System.out.println(verify);

        User user1 = JsonWebTokenPlus.verify(token, User.class);
        System.out.println(user1);

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
        System.out.println(token);

        boolean verify = JsonWebTokenPlus.verify(userId, token);
        System.out.println(verify);

        User user1 = JsonWebTokenPlus.verify(userId, token, User.class);
        System.out.println(user1);

        long expectedValue = 100L;
        long actualValue = user1.getUserId();
        Assertions.assertEquals(expectedValue, actualValue);
    }

}
