package com.starfish.extension.jwt;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * JsonWebPayload
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2021-02-12
 */
@Data
public class Payload implements Serializable {

    /**
     * 签发者
     */
    private String iss;

    /**
     * 面向的用户
     */
    private String sub;

    /**
     * 接收jwt的一方
     */
    private String aud;

    /**
     * 过期时间戳，jwt的过期时间，这个过期时间必须要大于签发时间
     */
    private Date exp;

    /**
     * 定义在什么时间之前，该jwt都是不可用的
     */
    private Date nbf;

    /**
     * 签发时间
     */
    private Date iat;

    /**
     * 唯一身份标识，主要用来作为一次性token,从而回避重放攻击
     */
    private String jti;

}
