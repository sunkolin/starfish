package com.starfish.core.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdcardUtil;
import com.starfish.core.constant.Constant;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * IdCardUtil
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2021-07-08
 */
@Slf4j
public class IdCardUtil {

    private IdCardUtil() {

    }

    /**
     * 判断身份证是否有效
     *
     * @param idCard 身份证号码
     * @return 结果，true有效，false无效
     */
    public static boolean validateIdCard(String idCard) {
        return IdcardUtil.isValidCard(idCard);
    }

    /**
     * 根据身份证获取生日
     *
     * @param idCard 身份证
     * @return 结果
     */
    public static String getBirthByIdCard(String idCard) {
        Date date = IdcardUtil.getBirthDate(idCard);
        return DateUtil.format(date, Constant.DATE_PATTERN);
    }

}
