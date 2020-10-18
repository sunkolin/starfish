package com.starfish.context;

import lombok.Data;

import java.util.Date;

/**
 * UserHolder 相对于UserContext更方便 但是必须在controller中使用
 * 需要配置在mvc:annotation-driven中配置mvc:argument-resolvers，com.sunny.context.ConstantSignUserHolderResolver
 * 在controller中直接
 * public Result select(UserHolder userHolder)
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

    /**
     * 角色
     */
    private String role;

    /**
     * 当前时间
     */
    private Date createTime = new Date();

}
