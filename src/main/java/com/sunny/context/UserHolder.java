package com.sunny.context;

import java.util.Date;
import java.util.Random;

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
public class UserHolder<T> {

    /**
     * 主键
     */
    private Long id;

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
     * 数据
     */
    private T object;

    /**
     * 当前时间
     */
    private Date createTime = new Date();

    /**
     * 一百万
     */
    private Integer random = new Random().nextInt(1000000);

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getRandom() {
        return random;
    }

    public void setRandom(Integer random) {
        this.random = random;
    }
}
