package com.starfish.core.client;

import com.starfish.core.client.result.WeatherModel;

/**
 * WeatherClient
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2021-07-08
 */
public interface WeatherClient {

    /**
     * 根据城市名称查询天气
     *
     * @param cityName 城市名称
     * @return 结果
     */
    WeatherModel getWeather(String cityName);
}
