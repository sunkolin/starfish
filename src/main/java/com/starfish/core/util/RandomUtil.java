package com.starfish.core.util;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadLocalRandom;

/**
 * RandomUtil
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2026-02-04
 */
@SuppressWarnings("unused")
@Slf4j
public class RandomUtil {

    /**
     * 生成指定范围的随机数
     *
     * @param min 最小值
     * @param max 最大值
     * @return 结果
     */
    public static int random(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }

    /**
     * 生成指定范围的随机数
     *
     * @param min 最小值
     * @param max 最大值
     * @return 结果
     */
    public static long random(long min, long max) {
        return ThreadLocalRandom.current().nextLong(min, max);
    }

}
