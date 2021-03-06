package com.starfish.extension.util;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Maps;
import com.starfish.trial.spring.RestTemplatePlus;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * WeatherPlus
 * 天气预报接口来源
 * https://blog.csdn.net/u011331731/article/details/72765410
 *
 * @author sunny
 * @version 1.0.0
 * @since 2020-10-30
 */
public final class WeatherPlus {

    /**
     * BAIDU_API_KEY
     */
    public static final String BAIDU_API_KEY = "25893504b880292c8472c48cc003a70a";

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
        httpHeaders.add("apikey", BAIDU_API_KEY);
        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);

        ResponseEntity<String> data = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
        String jsonResult = data.getBody();
        System.out.println(jsonResult);
        return jsonResult;
    }

    public static void main(String[] args) {
        getWeather2("北京");
    }

    public static String getWeather2(String cityName) {
        String url = "";
        url = url.replace("{cityName}", cityName);

        //设置请求头 apiKey
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add("apikey", BAIDU_API_KEY);
//        HttpEntity<String> httpEntity = new HttpEntity<>(null,null);

        Map<String, Object> params = new HashMap<>();
        params.put("city",cityName);
        ResponseEntity<String> data = RestTemplatePlus.exchange("http://wthrcdn.etouch.cn/weather_mini",HttpMethod.GET,params,new HashMap<>(),null,String.class);

//        ResponseEntity<String> data = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
        String jsonResult = data.getBody();
        System.out.println(jsonResult);
        return jsonResult;
    }

}
