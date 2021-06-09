package com.starfish;

import com.starfish.context.User;
import com.starfish.plus1.PinyinPlus;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * PinyinPlusTest
 *
 * @author sunny
 * @version 1.0.0
 * @since 2021-03-03
 */
public class PinyinPlusTest {

    @Test
    public void sortTest() {
        List<User> users = new ArrayList<>();

        User u1 = new User();
        u1.setUserId(1L);
        u1.setUserName("孙");
        users.add(u1);

        User u2 = new User();
        u2.setUserId(2L);
        u2.setUserName("马");
        users.add(u2);

        PinyinPlus.sort(users, new Function<User, String>() {
            @Override
            public String apply(User user) {
                return user.getUserName();
            }
        });

        System.out.println(users);
    }

}
