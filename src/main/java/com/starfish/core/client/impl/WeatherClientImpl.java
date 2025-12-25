package com.starfish.core.client.impl;

import com.starfish.core.client.WeatherClient;
import com.starfish.core.client.result.WeatherModel;
import com.starfish.core.util.RestTemplates;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * WeatherClientImpl
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2021-07-08
 */
@Slf4j
@Service
public class WeatherClientImpl implements WeatherClient {

    /**
     * 根据城市名称查询天气接口地址
     */
    private static final String GET_WEATHER_BY_CITY_NAME_URL = "https://wthrcdn.etouch.cn/weather_mini";

    @Override
    public WeatherModel getWeather(String cityName) {
        Map<String, String> params = Map.of("city", cityName);
        ResponseEntity<WeatherModel> responseEntity = RestTemplates.exchange(GET_WEATHER_BY_CITY_NAME_URL, HttpMethod.GET, null, params, null, WeatherModel.class);
        return responseEntity.getBody();
    }

}
