package com.starfish.module.id;

/**
 * IdentityCards
 *
 * @author sunny
 * @version 1.0.0
 * @since 2019-08-07
 */
public class IdentityCard {

    /**
     * 根据身份证获取生日
     *
     * @param identityCard 身份证
     * @return 结果
     */
    public static String getBirthday(String identityCard) {
        return identityCard.substring(6, 14);
    }

}
