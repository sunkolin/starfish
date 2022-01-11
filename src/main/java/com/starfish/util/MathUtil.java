package com.starfish.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * NumberTool
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2015-03-20
 */
@SuppressWarnings("unused")
public class MathUtil {

    /**
     * 百分号
     */
    private static final String PERCENT_SIGN = "%";

    /**
     * 千分号
     */
    private static final String PER_THOUSAND_SIGN = "‰";

    /**
     * 判断字符串是数字
     * copy from commons-lang NumberUtils
     *
     * @param string 字符串
     * @return 如果此字符串是数字则返回true，否则返回false
     */
    public static boolean isNumber(String string) {
        if (string == null || string.trim().length() <= 0) {
            return false;
        }
        try {
            String value = string;
            if (value.endsWith(PERCENT_SIGN) || value.endsWith(PER_THOUSAND_SIGN)) {
                value = value.substring(0, value.length() - 1);
            }
            new BigDecimal(value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 数字字符串转小数字符串
     * 科学计数法转小数字符串，百分比转小数，非数字转为0.00
     *
     * @param value 字符串
     * @return 结果
     */
    public static String toValue(String value) {
        String result = "0.00";

        if (value.endsWith(PERCENT_SIGN)) {
            result = divide(value.substring(0, value.length() - 1), "100");
        } else if (value.endsWith(PER_THOUSAND_SIGN)) {
            result = divide(value.substring(0, value.length() - 1), "1000");
        }
        //数字处理，非数字返回0.00
        else if (isNumber(value)) {
            result = new BigDecimal(value).toPlainString();
        }
        return result;
    }

    /**
     * 加法运算，不能加的字符串去掉，返回另外一个字符串
     *
     * @param first  被加数
     * @param second 加数
     * @return 结果
     */
    public static String plus(String first, String second) {
        String result = "0.00";
        try {
            result = new BigDecimal(toValue(first)).add(new BigDecimal(toValue(second))).toPlainString();
        } catch (Exception e) {
            return result;
        }
        return result;
    }

    /**
     * 减法运算
     *
     * @param first  被减数
     * @param second 减数
     * @return 结果
     */
    public static String minus(String first, String second) {
        String result = "0.00";
        try {
            result = new BigDecimal(toValue(first)).subtract(new BigDecimal(toValue(second))).toPlainString();
        } catch (Exception e) {
            return result;
        }
        return result;
    }

    /**
     * 相乘运算
     *
     * @param first  乘数
     * @param second 被乘数
     * @return 结果
     */
    public static String multiply(String first, String second) {
        String result = "0.00";
        try {
            result = new BigDecimal(toValue(first)).multiply(new BigDecimal(toValue(second))).toPlainString();
        } catch (Exception e) {
            return result;
        }
        return result;
    }

    /**
     * 相除后转换成小数
     *
     * @param first  被除数
     * @param second 除数
     * @return 结果
     */
    public static String divide(String first, String second) {
        String result = "0.00";
        try {
            result = new BigDecimal(toValue(first)).divide(new BigDecimal(toValue(second)), 4, RoundingMode.HALF_DOWN).toPlainString();
        } catch (Exception e) {
            return result;
        }
        return result;
    }

    /**
     * 相除后转换成百分比
     *
     * @param first  被除数
     * @param second 除数
     * @return 除法运算结果，百分比形式
     */
    public static String divideToPercent(String first, String second) {
        String result = "0.00%";
        try {
            result = toPercent(new BigDecimal(toValue(first)).divide(new BigDecimal(toValue(second)), 4, RoundingMode.HALF_DOWN).toPlainString());
        } catch (Exception e) {
            return result;
        }
        return result;
    }

    /**
     * 小数字符串转百分比
     *
     * @param number 小数
     * @return 百分比
     */
    public static String toPercent(String number) {
        String value = "0.00%";
        try {
            DecimalFormat df = new DecimalFormat("##.00%");
            value = df.format(number);
        } catch (Exception e) {
            return value;
        }
        return value;
    }

}
