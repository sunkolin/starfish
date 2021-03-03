package com.starfish.util;

import com.starfish.enumeration.ResultEnum;
import com.starfish.exception.CustomException;
import com.starfish.model.Result;

/**
 * Results
 *
 * @author sunny
 * @version 1.0.0
 * @since 2019-07-11
 */
@SuppressWarnings(value = "unused")
public class Results {

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

}
