package com.starfish.incubator.jwt;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;

import java.util.Map;

/**
 * JsonWebTokenPlus
 * JWT是由三段信息构成的，将这三段信息文本用.链接一起就构成了Jwt字符串。
 * 就像这样:eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjk5fQ.7DwG61h2GjZWA-sSUQA2jFVIenyOhz310rCUbCi09hY
 * 第一部分我们称它为头部（header),第二部分我们称其为载荷（payload, 类似于飞机上承载的物品)，第三部分是签证（signature).
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2022-07-26
 */
public class JsonWebTokenPlus {

    private static final String KEY = "Ks#ZKmIUTEj3@dU2";

    /**
     * constructor
     */
    private JsonWebTokenPlus() {
    }

    /**
     * 生成
     *
     * @param param 参数
     * @return 结果
     */
    public static <T> String create(T param) {
        Map<String, Object> map = BeanUtil.beanToMap(param, false, false);
        return create(map);
    }

    /**
     * 生成
     *
     * @param param 参数
     * @return 结果
     */
    public static String create(Map<String, Object> param) {
        return JWTUtil.createToken(param, KEY.getBytes());
    }

    /**
     * 验证
     *
     * @param token token
     * @return 结果
     */
    public static boolean verify(String token) {
        return JWTUtil.verify(token, KEY.getBytes());
    }

    /**
     * 解析
     *
     * @param token token
     * @param cls   class
     * @param <T>   T
     * @return 结果
     */
    public static <T> T parse(String token, Class<T> cls) {
        final JWT jwt = JWTUtil.parseToken(token);
        JSONObject payloads = jwt.getPayloads();
        return BeanUtil.toBean(payloads, cls);
    }

}
