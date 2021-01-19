package com.starfish.module.pinyin;

import java.util.Comparator;
import java.util.List;

/**
 * Pinyin
 *
 * @author sunny
 * @version 1.0.0
 * @since 2020-10-28
 */
@SuppressWarnings("unused")
public class Pinyin {

    /**
     * 汉字转拼音
     *
     * @param chinese 汉字
     * @return 拼音
     */
    public String getPinyin(String chinese) {
        return PinyinHelper.getPinyin(chinese);
    }

    /**
     * 获取汉字拼音的首字母
     *
     * @param chinese 汉字
     * @return 拼音
     */
    public String getFirstLetter(String chinese) {
        String result = "";
        String pinyin = PinyinHelper.getPinyin(chinese);
        if (pinyin.length() > 0) {
            result = pinyin.substring(0, 1);
        }
        return result;
    }

    /**
     * 按首字母排序，支持类型ChineseKeyValue与String，List中数据不可为null
     *
     * @param list 列表
     * @param <T>  T
     * @return 结果
     */
    public static <T> List<T> sort(List<T> list) {
        list.sort(Comparator.comparing(t -> {
            if (t instanceof ChineseKeyValue) {
                return PinyinHelper.getPinyin(((ChineseKeyValue) t).getValue());
            }
            return PinyinHelper.getPinyin(t.toString());
        }));
        return list;
    }

}
