package com.starfish.util;

import com.starfish.extension.slf4j.FormattingTuple;
import com.starfish.extension.slf4j.MessageFormatter;
import org.apache.commons.lang.text.StrBuilder;

import java.util.Locale;
import java.util.Random;

/**
 * StringUtil
 *
 * @author sunny
 * @version 1.0.0
 * @since 2015-03-20
 */
@SuppressWarnings(value = "unused")
public class StringUtil {

    /**
     * 定界符
     */
    private static final String DELIMITER = "{}";

    private static final String EMPTY = "";

    private static final int INDEX_NOT_FOUND = -1;

    /**
     * 判断字符串是null或空
     *
     * @param string 字符串
     * @return 结果
     */
    public static boolean isEmpty(String string) {
        return string == null || string.isEmpty();
    }

    /**
     * 判断字符串是null或空
     *
     * @param string 字符串
     * @return 结果
     */
    public static boolean isNotEmpty(String string) {
        return string != null && !string.isEmpty();
    }

    /**
     * 左侧填充字符串
     *
     * @param string    字符串
     * @param minLength 长度
     * @param padChar   字符
     * @return 结果
     * @see com.google.common.base.Strings
     */
    public static String padStart(String string, int minLength, char padChar) {
        checkNotNull(string);
        if (string.length() >= minLength) {
            return string;
        }
        StringBuilder sb = new StringBuilder(minLength);
        for (int i = string.length(); i < minLength; i++) {
            sb.append(padChar);
        }
        sb.append(string);
        return sb.toString();
    }

    /**
     * 右侧填充
     *
     * @param string    字符串
     * @param minLength 最小长度
     * @param padChar   字符
     * @return 结果
     * @see com.google.common.base.Strings
     */
    public static String padEnd(String string, int minLength, char padChar) {
        checkNotNull(string);
        if (string.length() >= minLength) {
            return string;
        }
        StringBuilder sb = new StringBuilder(minLength);
        sb.append(string);
        for (int i = string.length(); i < minLength; i++) {
            sb.append(padChar);
        }
        return sb.toString();
    }

    /**
     * 截取字符串
     *
     * @param string 字符串
     * @param start  开始
     * @param end    结束
     * @return 结果
     * @see org.apache.commons.lang.StringUtils
     */
    public static String substring(String string, int start, int end) {
        if (string == null) {
            return null;
        }

        // handle negatives
        if (end < 0) {
            // remember end is negative
            end = string.length() + end;
        }
        if (start < 0) {
            // remember start is negative
            start = string.length() + start;
        }

        // check length next
        if (end > string.length()) {
            end = string.length();
        }

        // if start is greater than end, return ""
        if (start > end) {
            return EMPTY;
        }

        if (start < 0) {
            start = 0;
        }
        if (end < 0) {
            end = 0;
        }

        return string.substring(start, end);
    }

    /**
     * 替换字符串
     *
     * @param text         字符串
     * @param searchString 需要替换的字符串
     * @param replacement  新字符串
     * @return 结果
     * @see org.apache.commons.lang.StringUtils
     */
    public static String replace(String text, String searchString, String replacement) {
        return replace(text, searchString, replacement, -1);
    }

    /**
     * 替换字符串
     *
     * @param text         字符串
     * @param searchString 需要替换的字符串
     * @param replacement  新字符串
     * @param max          最大替换次数
     * @return 结果
     * @see org.apache.commons.lang.StringUtils
     */
    public static String replace(String text, String searchString, String replacement, int max) {
        if (isEmpty(text) || isEmpty(searchString) || replacement == null || max == 0) {
            return text;
        }
        int start = 0;
        int end = text.indexOf(searchString, start);
        if (end == INDEX_NOT_FOUND) {
            return text;
        }
        int replLength = searchString.length();
        int increase = replacement.length() - replLength;
        increase = (increase < 0 ? 0 : increase);
        increase *= (max < 0 ? 16 : (max > 64 ? 64 : max));
        StrBuilder buf = new StrBuilder(text.length() + increase);
        while (end != INDEX_NOT_FOUND) {
            buf.append(text.substring(start, end)).append(replacement);
            start = end + replLength;
            if (--max == 0) {
                break;
            }
            end = text.indexOf(searchString, start);
        }
        buf.append(text.substring(start));
        return buf.toString();
    }

    /**
     * null转空字符串
     *
     * @param string 字符串
     * @return 结果
     */
    public static String nullToEmpty(String string) {
        return (string == null) ? "" : string;
    }

    /**
     * 随机指定个数的字符串
     *
     * @param size 字符串长度
     * @return 结果
     */
    public static String randomString(int size) {
        return randomString("0123456789abcdefghijklmnopqrstuvwxyz", size);
    }

    /**
     * 随机字符串
     *
     * @param string 指定字符串
     * @param size   长度
     * @return 结果
     */
    public static String randomString(String string, int size) {
        char[] chars = new char[size];
        for (int i = 0; i < size; i++) {
            Random random = new Random();
            chars[i] = string.charAt(random.nextInt(size));
        }
        return new String(chars);
    }

    /**
     * 首字母变小写
     *
     * @param string 字符串
     * @return 结果
     */
    public static String firstCharToLowerCase(String string) {
        String firstStr = string.substring(0, 1);
        return firstStr.toLowerCase(Locale.getDefault()) + string.substring(1);
    }

    /**
     * 首字母变大写
     *
     * @param string 字符串
     * @return 结果
     */
    public static String firstCharToUpperCase(String string) {
        String firstStr = string.substring(0, 1);
        return firstStr.toUpperCase(Locale.getDefault()) + string.substring(1);
    }

    /**
     * 包含字母
     *
     * @param string 字符串
     * @return 结果
     */
    public static boolean containCharacter(String string) {
        boolean result = false;
        char[] chars = string.toCharArray();
        for (char c : chars) {
            if (Character.isLetter(c)) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * 包含数字
     *
     * @param string 字符串
     * @return 结果
     */
    public static boolean containNumber(String string) {
        boolean result = false;
        char[] chars = string.toCharArray();
        for (char c : chars) {
            if (Character.isDigit(c)) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * 反转
     *
     * @param string 字符串
     * @return 结果
     */
    public static String reverse(String string) {
        if (string == null) {
            return null;
        }
        return new StringBuilder(string).reverse().toString();
    }

    /**
     * 如果第一个字符串是指定字符串则删除此字符串 如果最后一个字符串是指定字符串则删除此字符串
     *
     * @param string 字符串
     * @param value  指定字符串
     * @return 结果
     */
    public static String trimString(String string, String value) {
        String result = string;
        if (string.startsWith(value)) {
            result = string.substring(1);
        }
        if (string.endsWith(value)) {
            result = string.substring(0, string.length() - 1);
        }
        return result;
    }

    /**
     * 格式化文本
     *
     * @param string    文本模板，被替换的部分用 {} 表示
     * @param arguments 参数值
     * @return 格式化后的文本
     */
    public static String format(String string, Object... arguments) {
        FormattingTuple ft = MessageFormatter.arrayFormat(string, arguments);
        return ft.getMessage();
    }

    public static String simpleFormat(String string, Object... arguments) {
        StringBuilder sb = new StringBuilder(string);
        for (Object argument : arguments) {
            int index = sb.indexOf(DELIMITER);
            if (index > -1) {
                sb.replace(index, index + 2, argument == null ? "null" : argument.toString());
            } else {
                sb.append(",").append(argument == null ? "null" : argument.toString());
            }
        }
        return sb.toString();
    }

    /**
     * Ensures that an object reference passed as a parameter to the calling method is not null.
     *
     * @param reference an object reference
     * @param <T> T
     * @return the non-null reference that was validated
     * @throws NullPointerException if {@code reference} is null
     * @see com.google.common.base.Strings
     */
    public static <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        }
        return reference;
    }

    /**
     * return string == null || string.isEmpty();
     *
     * @param string param
     * @return result
     */
    public static boolean isNullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }

}
