package com.starfish;

import cn.hutool.core.date.DateUtil;

import java.util.Date;

/**
 * Test
 *
 * @author sunny
 * @version 1.0.0
 * @since 2020-10-28
 */
public class Test {

    public static void main(String[] args) {
        Date d = DateUtil.endOfDay(DateUtil.yesterday());
        System.out.println(d);
    }

}
