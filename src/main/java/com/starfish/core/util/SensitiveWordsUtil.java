package com.starfish.core.util;

import com.starfish.core.exception.CustomException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

/**
 * SensitiveWordsUtil
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2021-07-08
 */
@Slf4j
public class SensitiveWordsUtil {

    /**
     * sensitive words list
     */
    private static final List<String> SENSITIVE_WORD_LIST;

    static {
        try {
            SENSITIVE_WORD_LIST = FileUtil.readLines("classpath:words.txt");
        } catch (IOException e) {
            throw new CustomException(e);
        }
    }

    private SensitiveWordsUtil() {

    }

    /**
     * check sensitive words
     * 判断给定的字符串是否包含敏感词
     *
     * @param word word
     * @return return
     */
    public static boolean containsSensitiveWords(String word) {
        return SENSITIVE_WORD_LIST.contains(word);
    }

}
