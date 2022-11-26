package com.starfish.core.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * ValidatorTest
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2015-03-25
 */
class ValidatorTest {

    @Test
    void emailFormatTest() {
        Assertions.assertDoesNotThrow(() -> Validator.validateEmail("starfish@qq.com", -1, "邮箱格式不正确"));
    }

}
