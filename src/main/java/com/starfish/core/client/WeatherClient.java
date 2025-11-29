package com.starfish.core.client;

import com.starfish.core.model.weather.WeatherModel;
import com.starfish.core.util.RestTemplatePlus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.Map;

/**
 * WeatherClient
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2021-07-08
 */
@Slf4j
public class WeatherClient {

    /**
     * 根据城市名称查询天气接口地址
     */
    private static final String GET_WEATHER_BY_CITY_NAME_URL = "https://wthrcdn.etouch.cn/weather_mini";

    private WeatherClient() {

    }

    /**
     * 根据城市名称查询天气
     *
     * @param cityName 城市名称
     * @return 结果
     */
    public static WeatherModel getWeather(String cityName) {
        Map<String, String> params = Map.of("city", cityName);
        ResponseEntity<WeatherModel> responseEntity = RestTemplatePlus.exchange(GET_WEATHER_BY_CITY_NAME_URL, HttpMethod.GET, null, params, null, WeatherModel.class);
        return responseEntity.getBody();
    }

}
