package com.starfish.core.util;

import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;

import java.util.regex.Pattern;

/**
 * MobileUtil
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2021-07-08
 */
@Slf4j
public class MobileUtil {

    /**
     * <a href="https://www.cnblogs.com/zjk1/p/8623965.html">参考文档</a>
     */
    private static final String MOBILE_REGEX = "(?:0|86|\\+86)?1[3-9]\\d{9}";

    private static final int MOBILE_LENGTH = 11;

    private MobileUtil() {

    }

    /**
     * 判断一个字符串是否是手机号
     *
     * @param mobile 手机号
     * @return 结果
     */
    public static boolean isMobile(String mobile) {
        if (Strings.isNullOrEmpty(mobile)) {
            return false;
        }
        if (mobile.length() != MOBILE_LENGTH) {
            return false;
        }
        return Pattern.matches(MOBILE_REGEX, mobile);
    }

}
