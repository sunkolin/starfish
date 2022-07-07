package com.starfish.core.model;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * Result
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2012-8-15
 */
@SuppressWarnings({"unused", "unchecked"})
public class Result<T> implements Serializable {

    private transient static final Integer SUCCESS_CODE = 0;

    private transient static final String SUCCESS_MESSAGE = "success";

    /**
     * code
     */
    private Integer code;

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

    public Result(Object object) {
        // 验证参数不能为空
        if (object instanceof Result) {
            this.code = SUCCESS_CODE;
            this.message = SUCCESS_MESSAGE;
            this.data = (T) ((Result<?>) object).getData();
        } else {
            this.code = SUCCESS_CODE;
            this.message = SUCCESS_MESSAGE;
            this.data = (T) object;
        }
        // 如果同时有code和message字段，则只设置只两个字段并返回；否则编码码和信息设置为成功，并设置消息体
//        if (hasCodeAndMessage(data)) {
//            try {
//                Method getCode = data.getClass().getMethod("getCode");
//                getCode.setAccessible(true);
//                this.code = (int) getCode.invoke(data);
//
//                Method getMessage = data.getClass().getMethod("getMessage");
//                getMessage.setAccessible(true);
//                this.message = (String) getMessage.invoke(data);
//            } catch (Exception e) {
//                e.printStackTrace();
//                this.code = SYSTEM_EXCEPTION_CODE;
//                this.message = SYSTEM_EXCEPTION_MESSAGE;
//            }
//        }
    }

    public Result(Integer code, String message) {
        this.code = code;
        this.message = message;
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
                && result.getCode() != null
                && result.getCode().equals(SUCCESS_CODE);
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
        return new Result<>(t);
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
        Result<T> result = new Result<>();
        result.setCode(code);
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
    public static <T> Result<T> response(Integer code, String message) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    /**
     * 判断是否有code和message字段
     *
     * @param object 对象
     */
    private boolean hasCodeAndMessage(Object object) {
        try {
            Method getCode = object.getClass().getMethod("getCode");
            Method getMessage = object.getClass().getMethod("getMessage");
        } catch (NoSuchMethodException e) {
            return false;
        }
        return true;
    }

}
