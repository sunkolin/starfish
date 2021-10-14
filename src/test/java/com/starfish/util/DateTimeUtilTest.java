package com.starfish.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * DateTimeUtilTest
 *
 * @author sunny
 * @version 1.0.0
 * @since 2015-01-05
 */
public class DateTimeUtilTest {

    @Test
    public void dateBetween() {
        List<String> dates = DateTimeUtil.dateBetween("2014-01-01", "2014-01-03");
        System.out.println(dates);
        Assert.assertEquals(dates, Arrays.asList("2014-01-01", "2014-01-02", "2014-01-03"));
    }

}
