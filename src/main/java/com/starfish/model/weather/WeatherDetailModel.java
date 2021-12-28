package com.starfish.model.weather;

import lombok.Data;

import java.io.Serializable;

/**
 * WeatherDetailModel
 *
 * @author neacle
 * @version 1.0.0
 * @since 2021-03-08
 */
@Data
public class WeatherDetailModel implements Serializable {

    /**
     * 日期
     */
    private String date;

    /**
     * 最低温度
     */
    private String low;

    /**
     * 最高温度
     */
    private String high;

    /**
     * 天气
     */
    private String type;

}
