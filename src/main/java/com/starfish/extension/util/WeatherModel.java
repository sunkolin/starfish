package com.starfish.extension.util;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * WeatherModel
 *
 * @author sunny
 * @version 1.0.0
 * @since 2021-03-08
 */
@Slf4j
public class WeatherModel {

    /**
     * 昨日天气
     */
    private WeatherM yesterday;

    /**
     * 今日天气
     */
    private WeatherM today;

    /**
     * 未来7日天气
     */
    private List<WeatherM> forecast;

}
