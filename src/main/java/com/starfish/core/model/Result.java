package com.starfish.core.model;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

/**
 * Result
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2012-8-15
 */
@Slf4j
@SuppressWarnings({"unused"})
public class Result<T> {

    private static final Integer SUCCESS_CODE = 0;

    private static final String SUCCESS_MESSAGE = "success";

    private static final Integer FAIL_CODE = 500;

    private static final String FAIL_MESSAGE = "fail";

    /**
     * code
     */
    private int code;

    /**
     * message
     */
    private String message;

    /**
     * data
     */
    private T data;

    public Result() {
        this.code = SUCCESS_CODE;
        this.message = SUCCESS_MESSAGE;
    }

    private Result(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    private Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 返回成功结果
     *
     * @param <T> T
     * @return 结果
     */
    public static <T> Result<T> success() {
        return new Result<>(SUCCESS_CODE, SUCCESS_MESSAGE);
    }

    /**
     * 成功
     *
     * @param data 数据
     * @param <T>  T
     * @return 结果
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(SUCCESS_CODE, SUCCESS_MESSAGE, data);
    }

    /**
     * 成功
     *
     * @param data 数据
     * @param <T>  T
     * @return 结果
     */
    public static <T> Result<T> success(int code, String message, T data) {
        return new Result<>(code, message, data);
    }

    /**
     * 判断结果是否是成功
     *
     * @param result 参数
     * @return true成功，false失败
     */
    public static boolean success(Result<?> result) {
        return result != null
                && result.getCode() != null
                && result.getCode().equals(SUCCESS_CODE);
    }

    /**
     * 返回失败结果
     *
     * @param <T> T
     * @return 结果
     */
    public static <T> Result<T> fail() {
        return new Result<>(FAIL_CODE, FAIL_MESSAGE);
    }

    /**
     * 返回失败结果
     * 注意时传递的t需要有getCode和getMessage方法
     *
     * @param t   对象
     * @param <T> 类型
     * @return 结果
     */
    public static <T> Result<T> fail(T t) {
        return wrapper(t);
    }

    /**
     * 返回失败结果
     *
     * @param code    编码
     * @param message 信息
     * @param <T>     T
     * @return 结果
     */
    public static <T> Result<T> fail(Integer code, String message) {
        return new Result<>(code, message);
    }

    /**
     * 返回失败结果
     *
     * @param data 数据
     * @param <T>  T
     * @return 结果
     */
    public static <T> Result<T> fail(int code, String message, T data) {
        return new Result<>(code, message, data);
    }

    /**
     * 判断结果是否是失败
     *
     * @param result 参数
     * @return 结果
     */
    public static boolean fail(Result<?> result) {
        return result == null
                || result.getCode() == null
                || !result.getCode().equals(SUCCESS_CODE);
    }

    /**
     * 返回相应，不知结果是成功还是失败时使用
     *
     * @param code    编码
     * @param message 信息
     * @param <T>     T
     * @return 结果
     */
    public static <T> Result<T> wrapper(Integer code, String message) {
        return new Result<>(code, message);
    }

    /**
     * 返回相应，不知结果是成功还是失败时使用
     *
     * @param code    编码
     * @param message 信息
     * @param <T>     T
     * @return 结果
     */
    public static <T> Result<T> wrapper(Integer code, String message, T data) {
        return new Result<>(code, message, data);
    }

    /**
     * 包装结果，如果对象中有编码和描述，返回构建错误码对象；否则返回构建成功对象
     *
     * @param t   t
     * @param <T> T
     * @return 结果
     */
    public static <T> Result<T> wrapper(T t) {
        // 如果有getCode和getMessage方法
        // 如果同时有code和message字段，则只设置只两个字段并返回；否则编码码和信息设置为成功，并设置消息体
        try {
            if (hasCodeAndMessage(t)) {
                Method getCodeMethod = t.getClass().getMethod("getCode");
                int code = (int) getCodeMethod.invoke(t);

                Method getMessageMethod = t.getClass().getMethod("getMessage");
                String message = (String) getMessageMethod.invoke(t);

                return new Result<>(code, message);
            }
        } catch (Exception e) {
            log.warn("get code and message exception.", e);
        }
        return new Result<>(SUCCESS_CODE, SUCCESS_MESSAGE, t);
    }

    /**
     * 判断是否有code和message字段
     *
     * @param object 对象
     */
    private static boolean hasCodeAndMessage(Object object) {
        try {
            Method getCode = object.getClass().getMethod("getCode");
            Method getMessage = object.getClass().getMethod("getMessage");
        } catch (NoSuchMethodException e) {
            return false;
        }
        return true;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
