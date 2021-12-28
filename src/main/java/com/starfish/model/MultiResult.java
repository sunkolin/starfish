package com.starfish.model;

import java.io.Serializable;

/**
 * MultiResult
 * 最多支持返回5个结果，不建议同一个方法返回太多结果
 * 如果存入数据格式与取出数据格式不一致，会报类型转换异常
 * 例如Exception in thread "main" java.lang.ClassCastException: java.util.ArrayList cannot be cast to java.lang.Long
 *
 * @author neacle
 * @version 1.0.0
 * @since 2021-12-12
 */
@SuppressWarnings({"unchecked", "unused"})
public class MultiResult implements Serializable {

    private Object first;

    private Object second;

    private Object third;

    private Object fourth;

    private Object fifth;

    public <T> T getFirst() {
        return (T) first;
    }

    public void setFirst(Object first) {
        this.first = first;
    }

    public <T> T getSecond() {
        return (T) second;
    }

    public void setSecond(Object second) {
        this.second = second;
    }

    public <T> T getThird() {
        return (T) third;
    }

    public void setThird(Object third) {
        this.third = third;
    }

    public <T> T getFourth() {
        return (T) fourth;
    }

    public void setFourth(Object fourth) {
        this.fourth = fourth;
    }

    public <T> T getFifth() {
        return (T) fifth;
    }

    public void setFifth(Object fifth) {
        this.fifth = fifth;
    }

}
