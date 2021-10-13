package com.starfish.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.starfish.enumeration.ResultEnum;
import com.starfish.exception.CustomException;
import com.starfish.model.weather.WeatherDetailModel;
import com.starfish.model.weather.WeatherModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

/**
 * CommonBusinessUtil
 *
 * @author sunny
 * @version 1.0.0
 * @since 2021-07-08
 */
@Slf4j
public class BusinessUtil {

    /**
     * 参考：https://www.cnblogs.com/zjk1/p/8623965.html
     */
    private static final String MOBILE_REGEX = "^[1](([3|5|8][\\d])|([4][4,5,6,7,8,9])|([6][2,5,6,7])|([7][^9])|([9][1,8,9]))[\\d]{8}$";

    private static final int MOBILE_LENGTH = 11;

    /**
     * sensitive words list
     */
    private static final List<String> SENSITIVE_WORD_LIST;

    static {
        SENSITIVE_WORD_LIST = FileUtil.readLines("classpath:words.txt");
    }

    private static final RestTemplate REST_TEMPLATE = new RestTemplate();

    /**
     * 请求天气预报接口成功状态码
     */
    private static final int SUCCESS_STATUS = 1000;

    /**
     * 根据城市名称查询天气接口地址
     */
    private static final String GET_WEATHER_BY_CITY_NAME_URL = "http://wthrcdn.etouch.cn/weather_mini";

    /**
     * 判断一个字符串是否是手机号
     *
     * @param mobile 手机号
     * @return 结果
     */
    public static boolean isMobile(String mobile) {
        if (Strings.isNullOrEmpty(mobile)) {
            return false;
        }
        if (mobile.length() != MOBILE_LENGTH) {
            return false;
        }
        return Pattern.matches(MOBILE_REGEX, mobile);
    }

    /**
     * 根据身份证获取生日
     *
     * @param identityCard 身份证
     * @return 结果
     */
    public static String getBirthday(String identityCard) {
        return identityCard.substring(6, 14);
    }

    /**
     * 获取身份证信息
     * TODO 已无法使用，需要寻找新的接口
     *
     * @param identityCard 身份证号码
     * @return 结果
     */
    public static String getIdCardInfo(String identityCard) {
        return new RestTemplate().getForObject("http://apistore.baidu.com/microservice/icardinfo?id=" + identityCard, String.class);
    }

    /**
     * check sensitive words
     * 判断给定的字符串是否包含敏感词
     *
     * @param word word
     * @return return
     */
    public static boolean containsSensitiveWords(String word) {
        return SENSITIVE_WORD_LIST.contains(word);
    }

    /**
     * 根据城市名称查询天气
     *
     * @param cityName 城市名称
     * @return 结果
     */
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
    private static String conventFromGzip(String string) {
        String result = "";

        if (org.assertj.core.util.Strings.isNullOrEmpty(string)) {
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
