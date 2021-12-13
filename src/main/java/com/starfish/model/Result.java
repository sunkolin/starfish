package com.starfish.model;

import com.starfish.enumeration.ResultEnum;
import com.starfish.exception.CustomException;

import java.io.Serializable;

/**
 * Result
 *
 * @author sunny
 * @version 1.0.0
 * @since 2012-7-23
 */
@SuppressWarnings("unused")
public class Result<T> implements Serializable {

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
        this.status = 0;
        this.message = "success";
    }

    public Result(T body) {
        this.status = 0;
        this.message = "success";
        this.body = body;
    }

    public Result(ResultEnum resultEnum) {
        this.status = resultEnum.getCode();
        this.message = resultEnum.getMessage();
    }

    public Result(CustomException exception) {
        this.status = exception.getCode();
        this.message = exception.getMessage();
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
                && result.getStatus().equals(ResultEnum.SUCCESS.getCode());
    }

    /**
     * 返回失败结果
     *
     * @param resultEnum 枚举
     * @param <T>        类型
     * @return 结果
     */
    public static <T> Result<T> fail(ResultEnum resultEnum) {
        Result<T> result = new Result<>();
        result.setStatus(resultEnum.getCode());
        result.setMessage(resultEnum.getMessage());
        return result;
    }

    /**
     * 返回失败结果
     *
     * @param exception 异常
     * @param <T>       T
     * @return 结果
     */
    public static <T> Result<T> fail(CustomException exception) {
        Result<T> result = new Result<>();
        result.setStatus(exception.getCode());
        result.setMessage(exception.getMessage());
        return result;
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
                || !result.getStatus().equals(ResultEnum.SUCCESS.getCode());
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

}
