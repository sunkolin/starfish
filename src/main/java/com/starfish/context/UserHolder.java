package com.starfish.context;

import lombok.Data;

/**
 * UserHolder
 *
 * @author sunny
 * @version 1.0.0
 * @since 2015-05-08
 */
@Data
@SuppressWarnings(value = "unused")
public class UserHolder {

    /**
     * 主键
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 昵称
     */
    private String nickname;

}
