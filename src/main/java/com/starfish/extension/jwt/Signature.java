package com.starfish.extension.jwt;

import lombok.Data;

import java.io.Serializable;

/**
 * Signature
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2022-07-26
 */
@Data
public class Signature implements Serializable {

    /**
     * 签名
     */
    private String signature;

}
