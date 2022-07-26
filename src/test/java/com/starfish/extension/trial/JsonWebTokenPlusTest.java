package com.starfish.extension.trial;

import com.starfish.core.context.User;
import com.starfish.extension.jwt.JsonWebTokenPlus;
import org.junit.Test;

/**
 * JsonWebTokenPlusTest
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2022-07-26
 */
public class JsonWebTokenPlusTest {

    @Test
    public void isMobileTest() {
        User user = new User();
        user.setUserId(99L);

        String token = JsonWebTokenPlus.create(user);
        System.out.println(token);

        boolean verify = JsonWebTokenPlus.verify(token);
        System.out.println(verify);

        User user1 = JsonWebTokenPlus.parse(token, User.class);
        System.out.println(user1);
    }

}
