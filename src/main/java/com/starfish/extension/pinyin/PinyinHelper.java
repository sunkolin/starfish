package com.starfish.extension.pinyin;

import lombok.extern.slf4j.Slf4j;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * PinyinHelper
 *
 * @author sunny
 * @version 1.0.0
 * @since 2020-10-28
 */
@Slf4j
public class PinyinHelper {

    private static final HanyuPinyinOutputFormat FORMAT = new HanyuPinyinOutputFormat();

    static {
        FORMAT.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        FORMAT.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
    }

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

}
