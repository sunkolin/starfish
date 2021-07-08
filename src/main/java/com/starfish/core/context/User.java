package com.starfish.core.context;

import lombok.Data;

import java.io.Serializable;

/**
 * User
 *
 * @author sunny
 * @version 1.0.0
 * @since 2015-05-08
 */
@Data
@SuppressWarnings(value = "unused")
public class User implements Serializable {

    /**
     * 主键
     */
    private Long userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 昵称
     */
    private String nickName;

}
