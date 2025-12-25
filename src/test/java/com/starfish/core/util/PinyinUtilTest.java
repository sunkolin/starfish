package com.starfish.core.util;

import com.starfish.core.context.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * PinyinTest
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2021-03-03
 */
@Slf4j
class PinyinUtilTest {

    @Test
    void sortTest() {
        List<User> users = new ArrayList<>();

        User u1 = new User();
        u1.setUserId(1L);
        u1.setUserName("孙");
        users.add(u1);

        User u2 = new User();
        u2.setUserId(2L);
        u2.setUserName("马");
        users.add(u2);

        log.info("排序前={}", users);
        PinyinUtil.sort(users, User::getUserName);
        log.info("排序后={}", users);
        Assertions.assertEquals("马", users.get(0).getUserName());
    }

}
