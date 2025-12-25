package com.starfish.core.client.result;

import lombok.Data;

/**
 * WeatherModel
 *
 * {
 *     "data":{
 *         "yesterday":{
 *             "date":"1日星期三",
 *             "high":"高温 33℃",
 *             "fx":"西南风",
 *             "low":"低温 19℃",
 *             "fl":"<![CDATA[2级]]>",
 *             "type":"晴"
 *         },
 *         "city":"北京",
 *         "forecast":[
 *             {
 *                 "date":"2日星期四",
 *                 "high":"高温 35℃",
 *                 "fengli":"<![CDATA[2级]]>",
 *                 "low":"低温 21℃",
 *                 "fengxiang":"南风",
 *                 "type":"多云"
 *             },
 *             {
 *                 "date":"3日星期五",
 *                 "high":"高温 33℃",
 *                 "fengli":"<![CDATA[2级]]>",
 *                 "low":"低温 23℃",
 *                 "fengxiang":"南风",
 *                 "type":"阴"
 *             },
 *             {
 *                 "date":"4日星期六",
 *                 "high":"高温 29℃",
 *                 "fengli":"<![CDATA[2级]]>",
 *                 "low":"低温 18℃",
 *                 "fengxiang":"北风",
 *                 "type":"多云"
 *             },
 *             {
 *                 "date":"5日星期天",
 *                 "high":"高温 28℃",
 *                 "fengli":"<![CDATA[3级]]>",
 *                 "low":"低温 18℃",
 *                 "fengxiang":"西北风",
 *                 "type":"晴"
 *             },
 *             {
 *                 "date":"6日星期一",
 *                 "high":"高温 28℃",
 *                 "fengli":"<![CDATA[3级]]>",
 *                 "low":"低温 17℃",
 *                 "fengxiang":"东风",
 *                 "type":"多云"
 *             }
 *         ],
 *         "ganmao":"感冒易发期，外出请适当调整衣物，注意补充水分。",
 *         "wendu":"35"
 *     },
 *     "status":1000,
 *     "desc":"OK"
 * }
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2021-03-08
 */
@Data
public class WeatherModel {

    /**
     * 状态
     */
    private Integer status;

    /**
     * 描述
     */
    private String desc;

    /**
     * 数据
     */
    private WeatherData data;

}
