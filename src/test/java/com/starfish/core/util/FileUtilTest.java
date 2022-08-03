package com.starfish.core.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * FileUtilTest
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2015-11-08
 */
class FileUtilTest {

    @Test
    void readTest() {
        String result = FileUtil.read("classpath:application-starfish.properties");
        Assertions.assertNotNull(result);

        String result2 = FileUtil.read("file:/etc/profile");
        System.out.println(result2);
        Assertions.assertNotNull(result2);
    }

    @Test
    void readLinesTest() {
        List<String> result = FileUtil.readLines("classpath:application-starfish.properties");
        Assertions.assertTrue(result != null && !result.isEmpty());

        List<String> result2 = FileUtil.readLines("file:/etc/profile");
        System.out.println(result2);
        Assertions.assertTrue(result2 != null && !result2.isEmpty());
    }

}
