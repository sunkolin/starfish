package com.starfish.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * FileUtilTest
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2015-11-08
 */
public class FileUtilTest {

    @Test
    public void readTest() {
        String result = FileUtil.read("classpath:application.properties");
        Assert.assertNotNull(result);

        String result2 = FileUtil.read("file:/etc/profile");
        System.out.println(result2);
        Assert.assertNotNull(result2);
    }

    @Test
    public void readLinesTest() {
        List<String> result = FileUtil.readLines("classpath:application.properties");
        Assert.assertTrue(result != null && !result.isEmpty());

        List<String> result2 = FileUtil.readLines("file:/etc/profile");
        System.out.println(result2);
        Assert.assertTrue(result2 != null && !result2.isEmpty());
    }

}
