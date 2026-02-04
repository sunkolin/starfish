package com.starfish.core.util;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * IdUtil
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2026-02-04
 */
@SuppressWarnings("unused")
@Slf4j
public class IdUtil {

    /**
     * 获取主键，返回18位数字，支持Long类型，在请求量较小系统中可以使用
     * long long的最大值：9223372036854775807
     * long long的最小值：-9223372036854775808
     *
     * @return 结果
     */
    public static Long getId() {
        return longId();
    }

    public static Long longId() {
        String id = stringId();
        return Long.valueOf(id);
    }

    /**
     * 获取主键，返回18位数字，支持Long类型，在请求量较小系统中可以使用
     * long long的最大值：9223372036854775807
     * long long的最小值：-9223372036854775808
     *
     * @return 结果
     */
    public static String stringId() {
        // 第一段：yyyyMMddHHmmss共14位
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String id1 = format.format(new Date());

        //第二段：4位随机数
        String id2 = StringUtil.random("0123456789", 4);

        String id = id1 + id2;
        log.info("get id,id={}", id);

        return id;
    }

    /**
     * 获取不带横线的 uuid
     *
     * @return uuid
     */
    public static String getUuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 获取带横线的 uuid
     *
     * @return uuid
     */
    public static String getUuidWithLine() {
        return UUID.randomUUID().toString();
    }

}
