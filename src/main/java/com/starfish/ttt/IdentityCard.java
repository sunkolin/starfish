package com.starfish.ttt;

import org.springframework.web.client.RestTemplate;

/**
 * IdentityCard
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

    /**
     * 获取身份证信息
     *
     * @param idCard id card
     * @return the info
     */
    public static String getIdCardInfo(String idCard) {
        return new RestTemplate().getForObject("http://apistore.baidu.com/microservice/icardinfo?id=" + idCard, String.class);
    }

}
