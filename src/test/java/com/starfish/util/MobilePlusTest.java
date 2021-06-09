package com.starfish.util;

import com.starfish.plus1.MobilePlus;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * MobileUtilTest
 *
 * @author sunny
 * @version 1.0.0
 * @since 2015-01-21
 */
public class MobilePlusTest {

    @Test
    public void isMobileTest() {
        assertTrue(MobilePlus.isMobile("18610815188"));

        assertTrue(MobilePlus.isMobile("13496215263"));

        assertTrue(MobilePlus.isMobile("13912345678"));
    }

}
