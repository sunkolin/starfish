package com.starfish.core.util;

import com.starfish.core.model.weather.WeatherModel;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * BusinessUtilTest
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2021-10-13
 */
@Slf4j
class BusinessUtilTest {

    @Test
    void isMobileTest() {
        boolean result1 = BusinessUtil.isMobile("110");
        Assertions.assertFalse(result1);

        boolean result2 = BusinessUtil.isMobile("18610815188");
        Assertions.assertTrue(result2);

        Assertions.assertTrue(BusinessUtil.isMobile("18610815188"));
        Assertions.assertTrue(BusinessUtil.isMobile("13496215263"));
        Assertions.assertTrue(BusinessUtil.isMobile("13912345678"));
    }

    @Test
    void getBirthdayTest() {
        String result = BusinessUtil.getBirthByIdCard("513436200010136655");
        Assertions.assertEquals("2000-10-13", result);
    }

    @Test
    void validateIdCardTest() {
        boolean result = BusinessUtil.validateIdCard("513436200010136655");
        Assertions.assertTrue(result);
    }

    @Test
    void containsSensitiveWordsTest() {
        String word = "考前答案";
        boolean result = BusinessUtil.containsSensitiveWords(word);
        Assertions.assertTrue(result);

        String word2 = "没有";
        boolean result2 = BusinessUtil.containsSensitiveWords(word2);
        Assertions.assertFalse(result2);
    }

    @Disabled("Network does not work")
    @Test
    void getWeatherTest() {
        String cityName = "北京市";
        WeatherModel result = BusinessUtil.getWeather(cityName);
        log.info(JsonUtil.toJson(result));
        Assertions.assertNotNull(result);
    }

}
