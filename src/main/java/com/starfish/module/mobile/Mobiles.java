package com.starfish.module.mobile;

import com.google.common.base.Strings;

import java.util.regex.Pattern;

/**
 * MobileUtil
 *
 * @author sunny
 * @version 1.0.0
 * @since 2019-08-14
 */
public class Mobiles {

    /**
     * 参考：https://www.cnblogs.com/zjk1/p/8623965.html
     */
    private static final String MOBILE_REGEX = "^[1](([3|5|8][\\d])|([4][4,5,6,7,8,9])|([6][2,5,6,7])|([7][^9])|([9][1,8,9]))[\\d]{8}$";

    private static final int MOBILE_LENGTH = 11;

    /**
     * 判断一个字符串是否是手机号
     *
     * @param mobile 手机号
     * @return 结果
     */
    public static boolean isMobile(String mobile) {
        if (Strings.isNullOrEmpty(mobile) || mobile.length() != MOBILE_LENGTH) {
            return false;
        }

        return Pattern.matches(MOBILE_REGEX, mobile);
    }

}
