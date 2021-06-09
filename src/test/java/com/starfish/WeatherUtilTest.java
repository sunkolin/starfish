package com.starfish;

import com.starfish.extension.weather.WeatherModel;
import com.starfish.extension.WeatherUtil;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

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
        WeatherModel result = WeatherUtil.getWeather(city);
        assertNotNull(result);
    }

}
