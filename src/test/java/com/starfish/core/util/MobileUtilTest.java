package com.starfish.core.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * BusinessUtilTest
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2021-10-13
 */
@Slf4j
class MobileUtilTest {

    @Test
    void isMobileTest() {
        boolean result1 = MobileUtil.isMobile("110");
        Assertions.assertFalse(result1);

        boolean result2 = MobileUtil.isMobile("18610815188");
        Assertions.assertTrue(result2);

        Assertions.assertTrue(MobileUtil.isMobile("18610815188"));
        Assertions.assertTrue(MobileUtil.isMobile("13496215263"));
        Assertions.assertTrue(MobileUtil.isMobile("13912345678"));
    }

}
