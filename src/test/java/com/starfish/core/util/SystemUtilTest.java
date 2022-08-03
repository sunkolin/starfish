package com.starfish.core.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * SystemUtilTest
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2015-06-26
 */
class SystemUtilTest {

    @Test
    void getSystemTest() {
        String system = SystemUtil.getSystem().toLowerCase();
        String basePath = SystemUtil.getBasePath().toLowerCase();

        Assertions.assertTrue(system.contains("windows") || system.contains("linux") || system.contains("unix") || system.contains("mac") || system.contains("os"));
        Assertions.assertTrue(basePath.contains("/"));
    }

}
