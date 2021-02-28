package com.starfish.util;

import com.starfish.ttt.MobileUtil;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * MobileUtilTest
 *
 * @author sunny
 * @version 1.0.0
 * @since 2015-01-21
 */
public class MobileUtilTest {

    @Test
    public void isMobileTest() {
        assertTrue(MobileUtil.isMobile("18610815188"));

        assertTrue(MobileUtil.isMobile("13496215263"));

        assertTrue(MobileUtil.isMobile("13912345678"));
    }

}
