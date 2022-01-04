package com.starfish.util;

import com.alibaba.fastjson.JSON;
import com.starfish.model.weather.WeatherModel;
import org.junit.Assert;
import org.junit.Test;

/**
 * CommonUtilTest
 *
 * @author suncolin
 * @version 1.0.0
 * @since 2015-01-21
 */
public class CommonUtilTest {

    @Test
    public void getPublicAddressTest() {
        String result = CommonUtil.getPublicAddress();
        System.out.println(result);
        Assert.assertNotNull(result);
    }

}
