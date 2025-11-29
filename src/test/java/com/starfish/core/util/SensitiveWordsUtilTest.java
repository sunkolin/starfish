package com.starfish.core.util;

import com.starfish.core.client.WeatherClient;
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
class SensitiveWordsUtilTest {

    @Test
    void containsSensitiveWordsTest() {
        String word = "考前答案";
        boolean result = SensitiveWordsUtil.containsSensitiveWords(word);
        Assertions.assertTrue(result);

        String word2 = "没有";
        boolean result2 = SensitiveWordsUtil.containsSensitiveWords(word2);
        Assertions.assertFalse(result2);
    }

}
