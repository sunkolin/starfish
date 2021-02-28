package com.starfish.ttt;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.starfish.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

/**
 * ShortUrl
 *
 * @author sunny
 * @version 1.0.0
 * @since 2020-10-30
 */
@Slf4j
public final class ShortUrlUtil {

    /**
     * 获取短网址，使用百度短网址服务
     *
     * @param url short url
     * @return data
     */
    public static String getShortUrl(String url) {
        String result = "";
        //call remote service
        String data = new RestTemplate().getForObject("http://www.dwz.cn/create.php?url=" + url, String.class);
        log.info("ShortUrl getShortUrl,url={},result={}", url, data);

        //process return data {"tinyurl":"http://www.dwz.cn/yes","status":0,"longurl":"http://www.baidu.com","err_msg":""}
        JSONObject jsonObject = JSON.parseObject(data);
        if (0 == jsonObject.getIntValue("status")) {
            result = jsonObject.getString("tinyurl");
        }

        return result;
    }


}
