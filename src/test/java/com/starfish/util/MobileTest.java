package com.starfish.util;

import com.starfish.ttt.Mobile;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * MobileUtilTest
 *
 * @author sunny
 * @version 1.0.0
 * @since 2015-01-21
 */
public class MobileTest {

    @Test
    public void isMobileTest() {
        assertTrue(Mobile.isMobile("18610815188"));

        assertTrue(Mobile.isMobile("13496215263"));

        assertTrue(Mobile.isMobile("13912345678"));
    }

}
