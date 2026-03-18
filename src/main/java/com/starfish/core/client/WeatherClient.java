package com.starfish.core.client;

import com.starfish.core.client.request.WeatherRequest;
import com.starfish.core.client.response.WeatherResponse;

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
     * @param weatherRequest 参数
     * @return 结果
     */
    WeatherResponse getWeather(WeatherRequest weatherRequest);
}
