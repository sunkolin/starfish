package com.starfish.extension.util;

import com.starfish.constant.Constant;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * Weather
 *
 * @author sunny
 * @version 1.0.0
 * @since 2020-10-30
 */
public final class WeatherPlus {

    /**
     * 查询天气
     *
     * @param cityName 城市名称
     * @return 结果
     */
    public static String getWeather(String cityName) {
        String url = "http://apis.baidu.com/apistore/weatherservice/recentweathers?cityname={cityName}";
        url = url.replace("{cityName}", cityName);

        //设置请求头 apiKey
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("apikey", Constant.BAIDU_API_KEY);
        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);

        ResponseEntity<String> data = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
        String jsonResult = data.getBody();
        System.out.println(jsonResult);
        return jsonResult;
    }

}
