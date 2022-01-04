package com.starfish.util;

import lombok.extern.slf4j.Slf4j;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

/**
 * Pinyin
 *
 * @author suncolin
 * @version 1.0.0
 * @since 2020-10-28
 */
@Slf4j
@SuppressWarnings("unused")
public class PinyinUtil {

    private static final HanyuPinyinOutputFormat FORMAT = new HanyuPinyinOutputFormat();

    static {
        FORMAT.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        FORMAT.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
    }

    /**
     * 汉字转拼音
     *
     * @param chinese 汉字
     * @return 拼音
     */
    public static String getPinyin(String chinese) {
        StringBuilder pinyin = new StringBuilder();
        char[] newChar = chinese.toCharArray();
        for (char c : newChar) {
            if (c > 128) {
                try {
                    pinyin.append(net.sourceforge.pinyin4j.PinyinHelper.toHanyuPinyinStringArray(c, FORMAT)[0]);
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    log.error("PinyinHelper getPinyin error.", e);
                }
            } else {
                pinyin.append(c);
            }
        }
        return pinyin.toString();
    }

    /**
     * 获取汉字拼音的首字母
     *
     * @param chinese 汉字
     * @return 拼音
     */
    public static String getFirstLetter(String chinese) {
        String result = "";
        String pinyin = getPinyin(chinese);
        if (pinyin.length() > 0) {
            result = pinyin.substring(0, 1);
        }
        return result;
    }

    /**
     * 按首字母排序，List中数据不可为null
     *
     * @param list         列表
     * @param keyExtractor keyExtractor
     * @param <T>          T
     * @return 结果
     */
    @SuppressWarnings({"all"})
    public static <T> List<T> sort(List<T> list, Function<T, String> keyExtractor) {
        Function<T, String> newKeyExtractor = new Function<T, String>() {
            @Override
            public String apply(T t) {
                String key = keyExtractor.apply(t);
                return PinyinUtil.getPinyin(key);
            }
        };
        list.sort(Comparator.comparing(newKeyExtractor));
        return list;
    }

}
