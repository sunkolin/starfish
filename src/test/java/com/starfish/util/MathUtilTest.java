package com.starfish.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * MathUtilTest
 *
 * @author sunny
 * @version 1.0.0
 * @since 2015-03-20
 */
public class MathUtilTest {

    @Test
    public void isNumberTest() {
        Assert.assertTrue(MathUtil.isNumber("0"));
        Assert.assertTrue(MathUtil.isNumber("0.0"));
        Assert.assertTrue(MathUtil.isNumber("1.2E5"));
        Assert.assertTrue(MathUtil.isNumber("1.2e5"));
        Assert.assertTrue(MathUtil.isNumber("-1.1"));
        Assert.assertTrue(MathUtil.isNumber("24%"));
        Assert.assertTrue(MathUtil.isNumber("24‰"));

        Assert.assertFalse(MathUtil.isNumber(null));
        Assert.assertFalse(MathUtil.isNumber("string"));
        Assert.assertFalse(MathUtil.isNumber("s"));
        Assert.assertFalse(MathUtil.isNumber(" "));
    }

    @Test
    public void calcTest() {
        Assert.assertEquals("2", MathUtil.plus("1", "1"));
        Assert.assertEquals("1.00", MathUtil.plus("1", "null"));
        Assert.assertEquals("1.00", MathUtil.plus("1", "str"));
        Assert.assertEquals("2.25", MathUtil.plus("1", "1.25"));
        Assert.assertEquals("1.2500", MathUtil.plus("1", "25%"));
        Assert.assertEquals("1.0250", MathUtil.plus("1", "25‰"));
        Assert.assertEquals("120001", MathUtil.plus("1", "1.2E5"));
        Assert.assertEquals("0.00", MathUtil.plus("s", "s"));
        Assert.assertEquals("0.00", MathUtil.divide("s", "s"));
        Assert.assertEquals("0.00%", MathUtil.divideToPercent("s", "s"));
    }

}
