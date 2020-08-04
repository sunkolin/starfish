package com.starfish.util;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * SystemToolTest
 *
 * @author sunny
 * @version 1.0.0
 * @since 2015-06-26
 */
public class SystemToolTest {

    @Test
    public void getSystemTest() {
        String system = SystemUtil.getSystem().toLowerCase();
        String basePath = SystemUtil.getBasePath().toLowerCase();

        assertTrue(system.contains("windows") || system.contains("linux") || system.contains("unix") || system.contains("mac") || system.contains("os"));
        assertTrue(basePath.contains("/"));
    }

}
