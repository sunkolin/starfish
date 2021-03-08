package com.starfish.extension.util;

import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.Date;

/**
 * WeatherM
 *
 * @author sunny
 * @version 1.0.0
 * @since 2021-03-08
 */
@Slf4j
public class WeatherM implements Serializable {

    /**
     * 日期
     */
    private Date date;

    /**
     * 最低温度
     */
    private int lowTemp;

    /**
     * 最高温度
     */
    private int highTemp;

    /**
     * 天气
     */
    private String type;

}
