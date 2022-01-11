package com.starfish.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdcardUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dtflys.forest.Forest;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.starfish.constant.Constant;
import com.starfish.enumeration.ResultEnum;
import com.starfish.exception.CustomException;
import com.starfish.model.weather.WeatherDetailModel;
import com.starfish.model.weather.WeatherModel;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * BusinessUtil
 *
 * @author sunkolin
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
     * 判断身份证是否有效
     *
     * @param idCard 身份证号码
     * @return 结果，true有效，false无效
     */
    public static boolean validateIdCard(String idCard) {
        return IdcardUtil.isValidCard(idCard);
    }

    /**
     * 根据身份证获取生日
     *
     * @param idCard 身份证
     * @return 结果
     */
    public static String getBirthByIdCard(String idCard) {
        Date date = IdcardUtil.getBirthDate(idCard);
        return DateUtil.format(date, Constant.DATE_PATTERN);
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
        Map<String, Object> params = ImmutableMap.of("city", cityName);
        String json = Forest.get(GET_WEATHER_BY_CITY_NAME_URL).addQuery(params).executeAsString();
        return buildWeatherModel(json);
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
