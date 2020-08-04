package com.starfish.validator;

import com.starfish.exception.CustomException;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validator
 *
 * @author sunny
 * @version 1.0.0
 * @since 2014-11-10
 */
@SuppressWarnings(value = "unused")
public class Validator {

    private static final String EMAIL_PATTERN = "\\b(^['_A-Za-z0-9-]+(\\.['_A-Za-z0-9-]+)*@([A-Za-z0-9-])+(\\.[A-Za-z0-9-]+)*((\\.[A-Za-z0-9]{2,})|(\\.[A-Za-z0-9]{2,}\\.[A-Za-z0-9]{2,}))$)\\b";

    /**
     * 验证字符串
     *
     * @param value        字符串
     * @param min          最小个数
     * @param max          最大个数
     * @param errorCode    错误码
     * @param errorMessage 错误信息
     */
    public static void validateString(String value, int min, int max, int errorCode, String errorMessage) {
        if (value == null && min > 0) {
            throw new CustomException(errorCode, errorMessage);
        } else if (value != null && (value.length() < min || value.length() > max)) {
            throw new CustomException(errorCode, errorMessage);
        }
    }

    /**
     * 验证日期时间
     *
     * @param datetime     日期时间字符串
     * @param pattern      格式
     * @param errorCode    错误码
     * @param errorMessage 错误信息
     */
    public static void validateDateTime(String datetime, String pattern, int errorCode, String errorMessage) {
        try {
            new SimpleDateFormat(pattern).parse(datetime);
        } catch (ParseException e) {
            throw new CustomException(errorCode, errorMessage);
        }
    }

    /**
     * 验证数字不为null
     *
     * @param value        数字
     * @param errorCode    错误码
     * @param errorMessage 错误信息
     */
    public static void validateNumber(Integer value, int errorCode, String errorMessage) {
        if (value == null) {
            throw new CustomException(errorCode, errorMessage);
        }
    }

    /**
     * 验证数字范围，包括min max
     *
     * @param value        数字
     * @param min          最小值
     * @param max          最大值
     * @param errorCode    错误码
     * @param errorMessage 错误信息
     */
    public static void validateNumber(Integer value, Integer min, Integer max, int errorCode, String errorMessage) {
        if (value == null || value < min || value > max) {
            throw new CustomException(errorCode, errorMessage);
        }
    }

    /**
     * 验证数字不为null
     *
     * @param value        数字
     * @param errorCode    错误码
     * @param errorMessage 错误信息
     */
    public static void validateNumber(Long value, int errorCode, String errorMessage) {
        if (value == null) {
            throw new CustomException(errorCode, errorMessage);
        }
    }

    /**
     * 验证数字范围，包括min max
     *
     * @param value        数字
     * @param min          最小值
     * @param max          最大值
     * @param errorCode    错误码
     * @param errorMessage 错误信息
     */
    public static void validateNumber(Long value, Long min, Long max, int errorCode, String errorMessage) {
        if (value == null || value < min || value > max) {
            throw new CustomException(errorCode, errorMessage);
        }
    }

    /**
     * 验证字符串必须是指定字符串中的一个
     *
     * @param value        字符串
     * @param values       指定字符串数组
     * @param errorCode    错误码
     * @param errorMessage 错误信息
     */
    public static void validateStringAmong(String value, String[] values, int errorCode, String errorMessage) {
        if (value == null || value.length() <= 0) {
            throw new CustomException(errorCode, errorMessage);
        }
        if (values == null || values.length <= 0) {
            throw new CustomException(errorCode, errorMessage);
        }
        for (String v : values) {
            if (v.equals(value)) {
                return;
            }
        }
        throw new CustomException(errorCode, errorMessage);
    }

    /**
     * @param value        value
     * @param errorCode    error code
     * @param errorMessage error message
     */
    public static void validate(Object value, int errorCode, String errorMessage) {
        if (value == null) {
            throw new CustomException(errorCode, errorMessage);
        }
    }

    /**
     * @param value        value
     * @param errorCode    error code
     * @param errorMessage error message
     */
    public static void validateNotNull(Object value, int errorCode, String errorMessage) {
        if (value == null) {
            throw new CustomException(errorCode, errorMessage);
        }
    }

    /**
     * validate url
     *
     * @param value        value
     * @param errorCode    code
     * @param errorMessage message
     */
    public static void validateUrl(String value, int errorCode, String errorMessage) {
        try {
            String tmpValue = value;
            if (tmpValue.startsWith("https://")) {
                tmpValue = "http://" + tmpValue.substring(8); // URL doesn't understand the https protocol, hack it
            }
            new URL(tmpValue);
        } catch (MalformedURLException e) {
            throw new CustomException(errorCode, errorMessage);
        }
    }

    /**
     * validate mail
     *
     * @param value        value
     * @param errorCode    code
     * @param errorMessage message
     */
    public static void validateEmail(String value, int errorCode, String errorMessage) {
        if (value == null) {
            throw new CustomException(errorCode, errorMessage);
        }
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(value);
        if (!matcher.matches()) {
            throw new CustomException(errorCode, errorMessage);
        }
    }

    /**
     * validate qq
     *
     * @param value        value
     * @param errorCode    code
     * @param errorMessage message
     */
    public static void validateQq(String value, int errorCode, String errorMessage) {
        if (value == null || value.length() > 11) {
            throw new CustomException(errorCode, errorMessage);
        }
        validateStringNumber(value, errorCode, errorMessage);

    }

    /**
     * validate mobile
     *
     * @param value        value
     * @param errorCode    code
     * @param errorMessage message
     */
    public static void validateMobile(String value, int errorCode, String errorMessage) {
        if (value == null || value.length() > 11) {
            throw new CustomException(errorCode, errorMessage);
        }
        validateStringNumber(value, errorCode, errorMessage);

    }

    /**
     * value must be number
     *
     * @param value        value
     * @param errorCode    code
     * @param errorMessage message
     */
    public static void validateStringNumber(String value, int errorCode, String errorMessage) {
        if (value == null) {
            throw new CustomException(errorCode, errorMessage);
        }
        for (int i = 0; i < value.length(); i++) {
            if (!Character.isDigit(value.charAt(i))) {
                throw new CustomException(errorCode, errorMessage);
            }
        }
    }

}
