package com.starfish.core.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * MathUtilTest
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2015-03-20
 */
class MathUtilTest {

    @Test
    void isNumberTest() {
        Assertions.assertTrue(MathUtil.isNumber("0"));
        Assertions.assertTrue(MathUtil.isNumber("0.0"));
        Assertions.assertTrue(MathUtil.isNumber("1.2E5"));
        Assertions.assertTrue(MathUtil.isNumber("1.2e5"));
        Assertions.assertTrue(MathUtil.isNumber("-1.1"));
        Assertions.assertTrue(MathUtil.isNumber("24%"));
        Assertions.assertTrue(MathUtil.isNumber("24‰"));
        Assertions.assertFalse(MathUtil.isNumber(null));
        Assertions.assertFalse(MathUtil.isNumber("string"));
        Assertions.assertFalse(MathUtil.isNumber("s"));
        Assertions.assertFalse(MathUtil.isNumber(" "));
    }

    @Test
    void calcTest() {
        Assertions.assertEquals("2", MathUtil.plus("1", "1"));
        Assertions.assertEquals("1.00", MathUtil.plus("1", "null"));
        Assertions.assertEquals("1.00", MathUtil.plus("1", "str"));
        Assertions.assertEquals("2.25", MathUtil.plus("1", "1.25"));
        Assertions.assertEquals("1.2500", MathUtil.plus("1", "25%"));
        Assertions.assertEquals("1.0250", MathUtil.plus("1", "25‰"));
        Assertions.assertEquals("120001", MathUtil.plus("1", "1.2E5"));
        Assertions.assertEquals("0.00", MathUtil.plus("s", "s"));
        Assertions.assertEquals("0.00", MathUtil.divide("s", "s"));
        Assertions.assertEquals("0.00%", MathUtil.divideToPercent("s", "s"));
    }

}
