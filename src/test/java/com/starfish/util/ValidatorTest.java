package com.starfish.util;

import org.junit.Test;

/**
 * ValidatorTest
 *
 * @author neacle
 * @version 1.0.0
 * @since 2015-03-25
 */
public class ValidatorTest {

    @Test
    public void emailTest() {
        Validator.validateEmail("starfish@qq.com", -1, "邮箱格式不正确");
    }

}
