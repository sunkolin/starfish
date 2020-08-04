package com.sunny.common;

import com.sunny.constant.SunnyConstant;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * SunnyConstantTest
 *
 * @author sunny
 * @version 1.0.0
 * @since 2015-11-08
 */
public class SunnyConstantTest {

    @Test
    public void getVersionTest() {
        String sunnyVersion = SunnyConstant.SUNNY_VERSION;
        assertEquals(sunnyVersion, "0.0.4-SNAPSHOT");
    }

}
