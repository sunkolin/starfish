package com.starfish.plus;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableMap;
import com.starfish.enumeration.ResultEnum;
import com.starfish.exception.CustomException;
import com.starfish.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Strings;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

/**
 * WeatherPlus
 * 天气预报接口来源 https://blog.csdn.net/u011331731/article/details/72765410
 * 乱码解决方案参考：Spring RestTemplate 调用天气预报接口乱码的解决 https://blog.csdn.net/kkkloveyou/article/details/79890300
 *
 * @author sunny
 * @version 1.0.0
 * @since 2020-10-30
 */
@Slf4j
public final class WeatherPlus {

    private static final RestTemplate REST_TEMPLATE = new RestTemplate();

    /**
     * 请求天气预报接口成功状态码
     */
    private static final int SUCCESS_STATUS = 1000;

    /**
     * 根据城市名称查询天气接口地址
     */
    private static final String GET_WEATHER_BY_CITY_NAME_URL = "http://wthrcdn.etouch.cn/weather_mini";

    public static WeatherModel getWeather(String cityName) {
        Map<String, Object> uriVariables = ImmutableMap.of("city", cityName);
        String finalUrl = CommonUtil.contact(GET_WEATHER_BY_CITY_NAME_URL, uriVariables);
        String jsonResult = REST_TEMPLATE.getForObject(finalUrl, String.class, new HashMap<>(20));
        String finalJsonResult = conventFromGzip(jsonResult);
        return buildWeatherModel(finalJsonResult);
    }

    /**
     * 处理gizp压缩的数据
     *
     * @param string 字符串
     * @return 结果
     */
    public static String conventFromGzip(String string) {
        String result = "";

        if (Strings.isNullOrEmpty(string)) {
            return result;
        }

        ByteArrayInputStream in = null;
        ByteArrayOutputStream out = null;
        GZIPInputStream gunzip = null;
        try {
            in = new ByteArrayInputStream(string.getBytes(StandardCharsets.ISO_8859_1));
            out = new ByteArrayOutputStream();
            gunzip = new GZIPInputStream(in);
            byte[] buffer = new byte[1024];
            int n;
            while ((n = gunzip.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }

            result = out.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
                if (gunzip != null) {
                    gunzip.close();
                }
            } catch (Exception e) {
                log.error("close stream error.", e);
            }
        }

        return result;
    }

    private static WeatherModel buildWeatherModel(String jsonResult) {
        JSONObject jsonObject = JSON.parseObject(jsonResult);

        // 解析状态与描述
        Integer status = jsonObject.getInteger("status");

        if (status == null || SUCCESS_STATUS != status) {
            throw new CustomException(ResultEnum.GET_WEATHER_EXCEPTION);
        }

        // 解析数据字段
        // 解析昨日天气
        JSONObject data = jsonObject.getJSONObject("data");
        JSONObject yesterdayJsonObject = data.getJSONObject("yesterday");
        WeatherDetailModel yesterday = buildByJsonObject(yesterdayJsonObject);

        // 解析今日天气
        JSONArray forecastJsonArray = data.getJSONArray("forecast");
        JSONObject todayJsonObject = forecastJsonArray.getJSONObject(0);
        WeatherDetailModel today = buildByJsonObject(todayJsonObject);

        // 解析未来5日天气
        List<WeatherDetailModel> forecast = new ArrayList<>();
        for (int i = 0; i < forecastJsonArray.size(); i++) {
            JSONObject tempJsonObject = forecastJsonArray.getJSONObject(i);
            WeatherDetailModel forecastWeatherDetailModel = buildByJsonObject(tempJsonObject);
            forecast.add(forecastWeatherDetailModel);
        }

        WeatherModel weatherModel = new WeatherModel();
        weatherModel.setYesterday(yesterday);
        weatherModel.setToday(today);
        weatherModel.setForecast(forecast);

        return weatherModel;
    }

    private static WeatherDetailModel buildByJsonObject(JSONObject jsonObject) {
        String date = jsonObject.getString("date");
        String low = jsonObject.getString("low");
        String high = jsonObject.getString("high");
        String type = jsonObject.getString("type");
        WeatherDetailModel weatherDetailModel = new WeatherDetailModel();
        weatherDetailModel.setDate(date);
        weatherDetailModel.setType(type);
        weatherDetailModel.setLow(low);
        weatherDetailModel.setHigh(high);
        return weatherDetailModel;
    }

}
