package com.starfish.core.client;

import com.starfish.core.client.impl.WeatherClientImpl;
import com.starfish.core.client.request.WeatherRequest;
import com.starfish.core.client.response.WeatherResponse;
import com.starfish.core.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * WeatherClientTest
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2021-10-13
 */
@Slf4j
class WeatherClientTest {

    @Disabled("Network does not work")
    @Test
    void getWeatherTest() {
        WeatherRequest weatherRequest = new WeatherRequest();
        weatherRequest.setCityName("北京市");
        WeatherResponse result = new WeatherClientImpl().getWeather(weatherRequest);
        log.info(JsonUtil.toJson(result));
        Assertions.assertNotNull(result);
    }

}
