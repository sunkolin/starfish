package com.starfish.core.constant;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * ConstantTest
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2015-11-08
 */
class ConstantTest {

    @Test
    void getVersionTest() {
        String expectedValue = "1.0.0-SNAPSHOT";
        String actualValue = Constant.FRAMEWORK_VERSION;
        Assertions.assertEquals(expectedValue, actualValue);
    }

}
