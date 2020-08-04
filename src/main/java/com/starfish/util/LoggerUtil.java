package com.starfish.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 日志工具类，废弃，使用@Slf4j替代
 *
 * @author sunny
 * @version 1.0.0
 * @since 2016-12-23
 */
@Deprecated
public class LoggerUtil {

    private static final Logger LOGGER = LogManager.getRootLogger();

    private LoggerUtil() {

    }

    public static Logger getLogger() {
        return LOGGER;
    }

    public static boolean isTraceEnabled() {
        return LOGGER.isTraceEnabled();
    }

    public static boolean isDebugEnabled() {
        return LOGGER.isDebugEnabled();
    }

    public static boolean isInfoEnabled() {
        return LOGGER.isInfoEnabled();
    }

    public static void trace(String message, Object... arguments) {
        LOGGER.trace(message, arguments);
    }

    public static void debug(String message, Object... arguments) {
        LOGGER.debug(message, arguments);
    }

    public static void info(String message, Object... arguments) {
        LOGGER.info(message, arguments);
    }

    public static void warn(String message, Object... arguments) {
        LOGGER.warn(message, arguments);
    }

    public static void warn(String message, Throwable throwable) {
        LOGGER.warn(message, throwable);
    }

    public static void error(String message, Object... arguments) {
        LOGGER.error(message, arguments);
    }

    public static void error(String message, Throwable throwable) {
        LOGGER.error(message, throwable);
    }

}
