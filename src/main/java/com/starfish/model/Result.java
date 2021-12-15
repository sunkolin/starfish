package com.starfish.model;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Result
 *
 * @author sunny
 * @version 1.0.0
 * @since 2012-8-15
 */
@SuppressWarnings("unused")
public class Result<T> implements Serializable {

    public transient static final Integer SUCCESS_STATUS = 200;

    public transient static final String SUCCESS_MESSAGE = "success";

    public transient static final Integer SYSTEM_EXCEPTION_STATUS = 500;

    public transient static final String SYSTEM_EXCEPTION_MESSAGE = "system error";

    /**
     * status
     */
    private Integer status;

    /**
     * message
     */
    private String message;

    /**
     * body
     */
    private T body;

    public Result() {
        this.status = SUCCESS_STATUS;
        this.message = SUCCESS_MESSAGE;
    }

    public Result(T body) {
        // 如果同时有code和message字段，则只设置只两个字段并返回；否则状态码和信息设置为成功，并设置消息体
        if (hasCodeAndMessage(body)) {
            try {
                Method getCode = body.getClass().getMethod("getCode");
                getCode.setAccessible(true);
                int code = (int) getCode.invoke(body);

                Method getMessage = body.getClass().getMethod("getMessage");
                getMessage.setAccessible(true);
                String message = (String) getMessage.invoke(body);
            } catch (Exception e) {
                e.printStackTrace();
                this.status = SYSTEM_EXCEPTION_STATUS;
                this.message = SYSTEM_EXCEPTION_MESSAGE;
            }
        } else {
            this.status = SUCCESS_STATUS;
            this.message = SUCCESS_MESSAGE;
            this.body = body;
        }
    }

    public Result(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    /**
     * 返回成功结果
     *
     * @param <T> T
     * @return 结果
     */
    public static <T> Result<T> success() {
        return new Result<>();
    }

    /**
     * 成功
     *
     * @param data 数据
     * @param <T>  T
     * @return 结果
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(data);
    }

    /**
     * 判断结果是否是成功
     *
     * @param result 参数
     * @return true成功，false失败
     */
    public static boolean success(Result<?> result) {
        return result != null
                && result.getStatus() != null
                && result.getStatus().equals(SUCCESS_STATUS);
    }

    /**
     * 返回失败结果
     *
     * @param t   对象
     * @param <T> 类型
     * @return 结果
     */
    public static <T> Result<T> fail(T t) {
        return new Result<>(t);
    }

    /**
     * 返回失败结果
     *
     * @param status  状态
     * @param message 信息
     * @param <T>     T
     * @return 结果
     */
    public static <T> Result<T> fail(Integer status, String message) {
        Result<T> result = new Result<>();
        result.setStatus(status);
        result.setMessage(message);
        return result;
    }

    /**
     * 判断结果是否是失败
     *
     * @param result 参数
     * @return 结果
     */
    public static boolean fail(Result<?> result) {
        return result == null
                || result.getStatus() == null
                || !result.getStatus().equals(SUCCESS_STATUS);
    }

    /**
     * 返回相应，不知结果是成功还是失败时使用
     *
     * @param status  状态
     * @param message 信息
     * @param <T>     T
     * @return 结果
     */
    public static <T> Result<T> response(Integer status, String message) {
        Result<T> result = new Result<>();
        result.setStatus(status);
        result.setMessage(message);
        return result;
    }

    /**
     * 判断是否有code和message字段
     *
     * @param object 对象
     */
    public boolean hasCodeAndMessage(Object object) {
        try {
            Method getCode = body.getClass().getMethod("getCode");
            Method getMessage = body.getClass().getMethod("getMessage");
        } catch (NoSuchMethodException e) {
            return false;
        }
        return true;
    }

    public void s(Object o) {
        try {
            Method method = body.getClass().getMethod("getCode");
            method.setAccessible(true);
            int s = (int) method.invoke(o);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
