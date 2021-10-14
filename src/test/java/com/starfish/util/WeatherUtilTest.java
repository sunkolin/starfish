package com.starfish.util;

import com.starfish.model.weather.WeatherModel;
import org.junit.Assert;
import org.junit.Test;

/**
 * WeatherPlusTest
 *
 * @author sunny
 * @version 1.0.0
 * @since 2021-03-10
 */
public class WeatherUtilTest {

    @Test
    public void weatherPlusTest() {
        String city = "北京";
        WeatherModel result = BusinessUtil.getWeather(city);
        Assert.assertNotNull(result);
    }

}
