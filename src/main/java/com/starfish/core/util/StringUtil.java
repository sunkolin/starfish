package com.starfish.core.util;

import cn.hutool.core.text.CharSequenceUtil;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;
import org.springframework.util.StringUtils;

import java.security.SecureRandom;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * StringUtil
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2015-03-20
 */
@SuppressWarnings("unused")
public class StringUtil {

    /**
     * 定界符
     */
    private static final String DELIMITER = "{}";

    private static final String EMPTY = "";

    private static final int INDEX_NOT_FOUND = -1;

    /**
     * 英文字母
     */
    public static final String LETTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * 数字
     */
    public static final String NUMBERS = "0123456789";

    /**
     * 特殊字符，验证字符串是否有特殊字符时使用
     */
    public static final String SPECIAL_CHARACTER = "`~!@#$%^&*()_+-=[]{}|;:'?/.<>,";

    private static final SecureRandom RANDOM = new SecureRandom();

    private StringUtil() {
        // constructor
    }

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
     * @param padString 字符
     * @return 结果
     * @see com.google.common.base.Strings
     */
    public static String padStart(String string, int minLength, String padString) {
        checkNotNull(string);
        checkNotNull(padString);
        if (padString.length() > 1) {
            throw new IllegalArgumentException();
        }
        if (string.length() >= minLength) {
            return string;
        }
        return padString.repeat(minLength - string.length()) + string;
    }

    /**
     * 右侧填充
     *
     * @param string    字符串
     * @param minLength 最小长度
     * @param padString 字符
     * @return 结果
     * @see com.google.common.base.Strings
     */
    public static String padEnd(String string, int minLength, String padString) {
        checkNotNull(string);
        checkNotNull(padString);
        if (padString.length() > 1) {
            throw new IllegalArgumentException();
        }
        if (string.length() >= minLength) {
            return string;
        }
        return string + padString.repeat(minLength - string.length());
    }

    /**
     * 截取字符串
     *
     * @param string 字符串
     * @param start  开始
     * @param end    结束
     * @return 结果
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
     * @param string    字符串
     * @param oldString 需要替换的字符串
     * @param newString 新字符串
     * @return 结果
     */
    public static String replace(String string, String oldString, String newString) {
        return StringUtils.replace(string, oldString, newString);
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
    public static String random(int size) {
        return random("0123456789abcdefghijklmnopqrstuvwxyz", size);
    }

    /**
     * 随机字符串
     *
     * @param string 指定字符串
     * @param size   长度
     * @return 结果
     */
    public static String random(String string, int size) {
        char[] chars = new char[size];
        for (int i = 0; i < size; i++) {
            chars[i] = string.charAt(RANDOM.nextInt(string.length()));
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
    public static boolean containLetter(String string) {
        boolean result = false;
        String[] stringArray = string.split("");
        for (String item : stringArray) {
            if (LETTERS.contains(item)) {
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
     * 判断一个字符串是否只有字母
     *
     * @param string 字符串
     * @return 是返回true，否返回false
     */
    public static boolean onlyLetter(String string) {
        String[] stringArray = string.split("");
        for (String item : stringArray) {
            if (!LETTERS.contains(item)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断一个字符串是否只有数字
     *
     * @param string 字符串
     * @return 是返回true，否返回false
     */
    public static boolean onlyNumber(String string) {
        char[] chars = string.toCharArray();
        for (char c : chars) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断一个字符串是否是字母，数字和特殊字符
     *
     * @param string 字符串
     * @return 是返回true，否返回false
     */
    public static boolean onlyNumberLetterAndSpecialCharacter(String string) {
        String[] stringArray = string.split("");
        for (String item : stringArray) {
            if (!LETTERS.contains(item) && !NUMBERS.contains(item) && !SPECIAL_CHARACTER.contains(item)) {
                return false;
            }
        }
        return true;
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
     * 保留字符，第一个字符串中字符，在第二个字符串中出现过就保留，其余字符删除
     *
     * @param string 输入
     * @return 结果
     */
    public static String reserve(String string, String chars) {
        if (string == null) {
            return null;
        }

        StringBuilder result = new StringBuilder();
        for (char c : string.toCharArray()) {
            if (chars.contains(String.valueOf(c))) {
                result.append(c);
            }
        }

        return result.toString();
    }

    /**
     * 如果第一个字符串是指定字符串则删除此字符串 如果最后一个字符串是指定字符串则删除此字符串
     *
     * @param string 字符串
     * @param value  指定字符串
     * @return 结果
     */
    public static String trim(String string, String value) {
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
     * 格式化文本，有slf4j中的方法替换为hutool中方法
     *
     * @param string 文本模板，被替换的部分用 {} 表示
     * @param params 参数值
     * @return 格式化后的文本
     */
    public static String format(String string, Object... params) {
        return CharSequenceUtil.format(string, params);
    }

    /**
     * 格式化文本，相当于多个字符串连接
     *
     * @param params 参数值
     * @return 格式化后的文本
     */
    public static String format(Object... params) {
        String string = Strings.repeat("{}", params.length);
        return format(string, params);
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
     * @param <T>       T
     * @throws NullPointerException if {@code reference} is null
     * @see com.google.common.base.Strings
     */
    public static <T> void checkNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        }
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

    /**
     * 切分字符串
     *
     * @param string    字符串
     * @param separator 分割字符串
     * @return 结果
     */
    public static List<String> split(String string, String separator) {
        return Splitter.on(separator).splitToList(string);
    }

    /**
     * 切分字符串
     *
     * @param string    字符串
     * @param separator 分割字符串
     * @return 结果
     */
    public static List<Integer> splitToInteger(String string, String separator) {
        List<String> stringList = Splitter.on(separator).splitToList(string);
        return stringList.stream().map(Integer::valueOf).collect(Collectors.toList());
    }

    /**
     * 切分字符串
     *
     * @param string    字符串
     * @param separator 分割字符串
     * @return 结果
     */
    public static List<Long> splitToLong(String string, String separator) {
        List<String> stringList = Splitter.on(separator).splitToList(string);
        return stringList.stream().map(Long::valueOf).collect(Collectors.toList());
    }

    public static String toString(Map<?, ?> params) {
        return Joiner.on("&").useForNull("").withKeyValueSeparator("=").join(params);
    }

    public static String toString(List<?> list) {
        return Joiner.on(",").useForNull("null").join(list);
    }

    public static String toString(int[] array) {
        List<Integer> list = Ints.asList(array);
        return Joiner.on(",").useForNull("null").join(list);
    }

    public static String toString(long[] array) {
        List<Long> list = Longs.asList(array);
        return Joiner.on(",").useForNull("null").join(list);
    }

    public static String toString(String[] array) {
        return Joiner.on(",").useForNull("null").join(array);
    }

    public static String toString(Object[] array) {
        return Joiner.on(",").useForNull("null").join(array);
    }

}
