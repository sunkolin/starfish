package com.starfish.core.model.weather;

import lombok.Data;

import java.io.Serializable;

/**
 * WeatherDetailModel
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2021-03-08
 */
@Data
public class WeatherDetail implements Serializable {

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

    /**
     * 风向
     */
    private String fengxiang;

    /**
     * 风力
     */
    private String fengli;

}
