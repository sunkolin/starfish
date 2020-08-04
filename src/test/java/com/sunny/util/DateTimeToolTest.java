package com.sunny.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

/**
 * DateTimeToolTest
 *
 * @author sunny
 * @version 1.0.0
 * @since 2015-01-05
 */
public class DateTimeToolTest {

    @Test
    public void dateBetween() {
        List<String> dates = DateTimeUtil.dateBetween("2014-01-01", "2014-01-03");
        System.out.println(dates);
        assertEquals(dates, Arrays.asList("2014-01-01","2014-01-02","2014-01-03"));
    }

}
