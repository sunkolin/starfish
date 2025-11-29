package com.starfish.core.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * IdCardUtilTest
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2021-10-13
 */
@Slf4j
class IdCardUtilTest {

    @Test
    void getBirthdayTest() {
        String result = IdCardUtil.getBirthByIdCard("513436200010136655");
        Assertions.assertEquals("2000-10-13", result);
    }

    @Test
    void validateIdCardTest() {
        boolean result = IdCardUtil.validateIdCard("513436200010136655");
        Assertions.assertTrue(result);
    }

}
