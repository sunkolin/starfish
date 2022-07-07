package com.starfish.core.util;

/**
 * ZipUtil
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2022-03-11
 */
public class ZipUtil {

    private ZipUtil() {

    }

    /**
     * 压缩文件
     * ZipUtil.zip("d:/aaa", "d:/bbb/aaa.zip");
     *
     * @param source 源
     * @param target 目标
     */
    public static void zip(String source, String target) {
        cn.hutool.core.util.ZipUtil.zip(source, target);
    }

    /**
     * 解压文件
     * File unzip = ZipUtil.unzip("E:\\aaa\\test.zip", "e:\\aaa");
     *
     * @param source 源
     * @param target 目标
     */
    public static void unzip(String source, String target) {
        cn.hutool.core.util.ZipUtil.unzip(source, target);
    }

}
