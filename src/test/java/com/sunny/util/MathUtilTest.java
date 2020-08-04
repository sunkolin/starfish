package com.sunny.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * MathUtilTest
 *
 * @author sunny
 * @version 1.0.0
 * @since 2015-03-20
 */
public class MathUtilTest {

    @Test
    public void isNumberTest(){
        assertTrue(MathUtil.isNumber("0"));
        assertTrue(MathUtil.isNumber("0.0"));
        assertTrue(MathUtil.isNumber("1.2E5"));
        assertTrue(MathUtil.isNumber("1.2e5"));
        assertTrue(MathUtil.isNumber("-1.1"));
        assertTrue(MathUtil.isNumber("24%"));
        assertTrue(MathUtil.isNumber("24‰"));

        assertFalse(MathUtil.isNumber(null));
        assertFalse(MathUtil.isNumber("string"));
        assertFalse(MathUtil.isNumber("s"));
        assertFalse(MathUtil.isNumber(" "));
    }

    @Test
    public void calcTest(){
        assertEquals("2",MathUtil.plus("1","1"));
        assertEquals("1.00",MathUtil.plus("1","null"));
        assertEquals("1.00",MathUtil.plus("1","str"));
        assertEquals("2.25",MathUtil.plus("1","1.25"));
        assertEquals("1.2500",MathUtil.plus("1","25%"));
        assertEquals("1.0250",MathUtil.plus("1","25‰"));
        assertEquals("120001",MathUtil.plus("1","1.2E5"));
        assertEquals("0.00",MathUtil.plus("s","s"));
        assertEquals("0.00",MathUtil.divide("s", "s"));
        assertEquals("0.00%",MathUtil.divideToPercent("s","s"));
    }

}
