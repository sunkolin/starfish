package com.starfish.plus;

import com.starfish.util.FileUtil;

import java.util.List;

/**
 * SensitiveWordsPlus
 *
 * @author sunny
 * @version 1.0.0
 * @since 2021-03-06
 */
public final class SensitiveWordsPlus {

    /**
     * sensitive words list
     */
    private static final List<String> SENSITIVE_WORD_LIST;

    static {
        SENSITIVE_WORD_LIST = FileUtil.readLines("words.txt");
    }

    /**
     * check sensitive words
     * 判断给定的字符串是否包含敏感词
     *
     * @param words words
     * @return return
     */
    public static boolean contains(String... words) {
        // default flag is false
        boolean flag = false;
        for (String word : words) {
            if (SENSITIVE_WORD_LIST.contains(word)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

}
