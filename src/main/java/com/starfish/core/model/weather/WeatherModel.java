package com.starfish.core.model.weather;

import lombok.Data;

import java.util.List;

/**
 * WeatherModel
 *
 * @author sunny
 * @version 1.0.0
 * @since 2021-03-08
 */
@Data
public class WeatherModel {

    /**
     * 昨日天气
     */
    private WeatherDetailModel yesterday;

    /**
     * 今日天气
     */
    private WeatherDetailModel today;

    /**
     * 未来5日天气，包括今日
     */
    private List<WeatherDetailModel> forecast;

}
