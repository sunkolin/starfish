package com.starfish.experiment.jwt;

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
    @Deprecated
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
    public static <T> String create(Long userId, T param) {
        Map<String, Object> map = BeanUtil.beanToMap(param, false, false);
        return create(userId, map);
    }

    /**
     * 生成
     *
     * @param param 参数
     * @return 结果
     */
    @Deprecated
    public static String create(Map<String, Object> param) {
        return JWTUtil.createToken(param, KEY.getBytes());
    }

    /**
     * 生成
     *
     * @param param 参数
     * @return 结果
     */
    public static String create(Long userId, Map<String, Object> param) {
        return JWTUtil.createToken(param, (userId.toString() + KEY).getBytes());
    }

    /**
     * 验证
     *
     * @param token token
     * @return 结果
     */
    @Deprecated
    public static boolean verify(String token) {
        return JWTUtil.verify(token, KEY.getBytes());
    }

    /**
     * 验证
     *
     * @param token token
     * @return 结果
     */
    public static boolean verify(Long userId, String token) {
        return JWTUtil.verify(token, (userId.toString() + KEY).getBytes());
    }

    /**
     * 解析
     *
     * @param token token
     * @param cls   class
     * @param <T>   T
     * @return 结果
     */
    @Deprecated
    public static <T> T verify(String token, Class<T> cls) {
        // 验证token有效性
        boolean verifyResult = JWTUtil.verify(token, KEY.getBytes());
        if (!verifyResult) {
            return null;
        }

        // 解析token数据
        final JWT jwt = JWTUtil.parseToken(token);
        JSONObject payloads = jwt.getPayloads();
        return BeanUtil.toBean(payloads, cls);
    }

    /**
     * 验证并解析token
     *
     * @param token token
     * @param cls   class
     * @param <T>   T
     * @return 结果
     */
    public static <T> T verify(Long userId, String token, Class<T> cls) {
        // 验证token有效性
        boolean verifyResult = JWTUtil.verify(token, (userId.toString() + KEY).getBytes());
        if (!verifyResult) {
            return null;
        }

        // 解析token数据
        final JWT jwt = JWTUtil.parseToken(token);
        JSONObject payloads = jwt.getPayloads();
        return BeanUtil.toBean(payloads, cls);
    }

}
