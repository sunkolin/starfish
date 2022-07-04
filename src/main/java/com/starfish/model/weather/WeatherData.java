package com.starfish.model.weather;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * WeatherData
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2022-06-02
 */
@Data
public class WeatherData implements Serializable {

    private WeatherDetail yesterday;

    private String city;

    private List<WeatherDetail> forecast;

    private String ganmao;

    private String wendu;

}
