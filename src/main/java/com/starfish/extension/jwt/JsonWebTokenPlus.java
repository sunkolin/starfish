package com.starfish.extension.jwt;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;

import java.util.Map;

/**
 * JsonWebTokenPlus
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2022-07-26
 */
public class JsonWebTokenPlus {

    private static final String KEY = "1234567890";

    public JsonWebTokenPlus(){

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
