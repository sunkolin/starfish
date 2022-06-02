package com.starfish.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * CommonUtilTest
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2015-01-21
 */
public class CommonUtilTest {

    @Test
    public void getPublicAddressTest() {
        String result = CommonUtil.getPublicIp();
        System.out.println(result);
        Assert.assertNotNull(result);
    }

}
