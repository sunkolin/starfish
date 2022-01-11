package com.starfish.util;

import com.starfish.trial.EhcacheUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 * EhcacheUtilTest
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2016-05-19
 */
public class EhcacheUtilTest {

    @Test
    public void test() {
        String userCount = EhcacheUtil.get("commonCache", String.class, String.class, "userCount");
        Assert.assertNull(userCount);

        //设置用户数量100
        EhcacheUtil.put("commonCache", String.class, String.class, "userCount", "100");

        userCount = EhcacheUtil.get("commonCache", String.class, String.class, "userCount");
        Assert.assertEquals("100", userCount);

        // 清除
        EhcacheUtil.clear("commonCache", String.class, String.class);
        userCount = EhcacheUtil.get("commonCache", String.class, String.class, "userCount");
        Assert.assertNull(userCount);
    }

}
