package com.starfish.constant;

import org.junit.Assert;
import org.junit.Test;

/**
 * ConstantTest
 *
 * @author suncolin
 * @version 1.0.0
 * @since 2015-11-08
 */
public class ConstantTest {

    @Test
    public void getVersionTest() {
        String version = Constant.FRAMEWORK_VERSION;
        Assert.assertEquals(version, "1.0.0-SNAPSHOT");
    }

}
