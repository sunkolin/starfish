package com.starfish.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * SystemUtilTest
 *
 * @author sunny
 * @version 1.0.0
 * @since 2015-06-26
 */
public class SystemUtilTest {

    @Test
    public void getSystemTest() {
        String system = SystemUtil.getSystem().toLowerCase();
        String basePath = SystemUtil.getBasePath().toLowerCase();

        Assert.assertTrue(system.contains("windows") || system.contains("linux") || system.contains("unix") || system.contains("mac") || system.contains("os"));
        Assert.assertTrue(basePath.contains("/"));
    }

}
