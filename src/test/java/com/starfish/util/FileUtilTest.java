package com.starfish.util;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
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

        String result2 = FileUtil.read("file:/etc/profile");
        System.out.println(result2);
        assertNotNull(result2);
    }

    @Test
    public void readLinesTest() {
        List<String> result = FileUtil.readLines("classpath:application.properties");
        assertTrue(result != null && !result.isEmpty());

        List<String> result2 = FileUtil.readLines("file:/etc/profile");
        System.out.println(result2);
        assertTrue(result2 != null && !result2.isEmpty());
    }

}
