package com.starfish;

import com.starfish.extension.util.WeatherModel;
import com.starfish.extension.util.WeatherPlus;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * WeatherPlusTest
 *
 * @author sunny
 * @version 1.0.0
 * @since 2021-03-10
 */
public class WeatherPlusTest {

    @Test
    public void weatherPlusTest() {
        String city = "北京";
        WeatherModel result = WeatherPlus.getWeather(city);
        assertNotNull(result);
    }

}
