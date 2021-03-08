package com.starfish.extension.util;

import com.google.common.collect.ImmutableMap;
import com.starfish.trial.spring.RestTemplatePlus;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
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

    public static void main(String[] args) {
//        System.out.println(getWeather("北京"));

        String url = "http://wthrcdn.etouch.cn/weather_mini?city=北京";
        RestTemplate restTemplate = RestTemplatePlus.buildRestTemplate();
       String s =  restTemplate.getForObject(url,String.class,new HashMap<>());
        System.out.println(s);
        try {
            System.out.println(new String(s.getBytes("ISO-8859-1"),"GB2312"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static String getWeather(String cityName) {
        Map<String, Object> params = ImmutableMap.of("city", cityName);
        ResponseEntity<String> data = RestTemplatePlus.exchange("http://wthrcdn.etouch.cn/weather_mini", HttpMethod.GET, params, new HashMap<>(), null, String.class);
        String jsonResult = data.getBody();
        try {
//           String s =   new String( jsonResult.getBytes("UTF-8"),"GB2312");

            System.out.println(jsonResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonResult;
    }


}
