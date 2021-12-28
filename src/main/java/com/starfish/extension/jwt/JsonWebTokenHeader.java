package com.starfish.extension.jwt;

import lombok.Data;

import java.io.Serializable;

/**
 * JsonWebTokenHeader
 *
 * @author neacle
 * @version 1.0.0
 * @since 2021-02-12
 */
@Data
public class JsonWebTokenHeader implements Serializable {

    /**
     * 类型
     */
    private String alg;

    /**
     * 加密的算法
     */
    private String typ;

}
