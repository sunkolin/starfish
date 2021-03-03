package com.starfish.util;

import com.starfish.extension.cache.EhcachePlus;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * EhcacheToolTest
 *
 * @author sunny
 * @version 1.0.0
 * @since 2016-05-19
 */
public class EhcachePlusToolTest {

    @Test
    public void test() {
        String userCount = EhcachePlus.get("commonCache", String.class, String.class, "userCount");
        assertEquals(userCount, null);

        //设置用户数量100
        EhcachePlus.put("commonCache", String.class, String.class, "userCount", "100");

        userCount = EhcachePlus.get("commonCache", String.class, String.class, "userCount");
        assertEquals("100", userCount);

        // 清除
        EhcachePlus.clear("commonCache", String.class, String.class);
        userCount = EhcachePlus.get("commonCache", String.class, String.class, "userCount");
        assertEquals(userCount, null);
    }

}
