package com.starfish.util;

import com.alibaba.fastjson.JSON;
import com.starfish.model.weather.WeatherModel;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * BusinessUtilTEST
 *
 * @author sunny
 * @version 1.0.0
 * @since 2021-10-13
 */
public class BusinessUtilTest {

    @Test
    public void isMobileTest() {
        boolean result1 = BusinessUtil.isMobile("110");
        Assert.assertFalse(result1);

        boolean result2 = BusinessUtil.isMobile("18610815188");
        Assert.assertTrue(result2);
    }

    @Test
    public void getBirthdayTest() {
        String result = BusinessUtil.getBirthday("513436200010136655");
        Assert.assertEquals("20001013",result);
    }

    @Test
    public void getIdCardInfoTest() {
        String result = BusinessUtil.getIdCardInfo("513436200010136655");
        Assert.assertEquals("xxx",result);
    }

    @Test
    public void containsSensitiveWordsTest() {
        String word = "考前答案";
        boolean result = BusinessUtil.containsSensitiveWords(word);
        Assert.assertTrue(result);

        String word2 = "没有";
        boolean result2 = BusinessUtil.containsSensitiveWords(word2);
        Assert.assertFalse(result2);
    }

    @Test
    public void getWeatherTest() {
        WeatherModel result = BusinessUtil.getWeather("北京市");
        System.out.println(JSON.toJSONString(result));
        Assert.assertNotNull(result);
    }

}
