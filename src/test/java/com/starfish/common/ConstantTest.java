package com.starfish.common;

import com.starfish.constant.Constant;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * SunnyConstantTest
 *
 * @author sunny
 * @version 1.0.0
 * @since 2015-11-08
 */
public class ConstantTest {

    @Test
    public void getVersionTest() {
        String version = Constant.FRAMEWORK_VERSION;
        assertEquals(version, "1.0.0-SNAPSHOT");
    }

}
