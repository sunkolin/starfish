package com.starfish.core.util;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * FileUtilTest
 *
 * @author sunny
 * @version 1.0.0
 * @since 2015-11-08
 */
public class FileUtilTest {

    @Test
    public void readTest() {
        String result = FileUtil.read("classpath:application.properties");
        assertNotNull(result);
    }

    @Test
    public void readLinesTest() {
        List<String> result = FileUtil.readLines("classpath:application.properties");
        assertTrue(result != null && !result.isEmpty());
    }

}
