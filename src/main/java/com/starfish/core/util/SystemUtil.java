package com.starfish.core.util;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

/**
 * SystemUtil
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2015-06-26
 */
@Slf4j
public class SystemUtil {

    /**
     * windows操作系统关键字
     */
    private static final String WINDOWS_SYSTEM_NAME_KEYWORDS = "windows";

    private SystemUtil(){
        // constructor
    }

    /**
     * 获取默认路径
     *
     * @return 路径
     */
    public static String getBasePath() {
        return getBasePathPrefix() + File.separator;
    }

    public static String getBasePathPrefix() {
        String result;
        String systemName = getSystem();
        if (systemName != null && systemName.toLowerCase().contains(WINDOWS_SYSTEM_NAME_KEYWORDS)) {
            File d = new File("d:");
            if (d.exists() && d.canWrite()) {
                result = "d:";
            } else {
                result = "c:";
            }
        } else {
            result = "";
        }
        return result;
    }

    /**
     * 获取临时路径
     *
     * @return 临时路径字符串
     */
    public static String getTmpPath() {
        return getBasePathPrefix() + File.separator + "tmp" + File.separator;
    }

    /**
     * 获取当前操作系统名称
     *
     * @return 操作系统名称
     */
    public static String getSystem() {
        return System.getProperty("os.name");
    }

    /**
     * 获取SystemLoadAverage
     *
     * @return 结果
     */
    public static double getLoadAverage() {
        double loadAverage = 0.0D;

        try {
            OperatingSystemMXBean operatingSystemMxBean = ManagementFactory.getOperatingSystemMXBean();
            loadAverage = operatingSystemMxBean.getSystemLoadAverage();
        } catch (Exception e) {
            log.error("getLoadAverage error.", e);
        }

        return loadAverage;
    }

}
