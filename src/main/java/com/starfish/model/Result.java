package com.starfish.model;

import com.starfish.enums.ResultEnum;
import com.starfish.exception.CustomException;

import java.io.Serializable;

/**
 * Result
 *
 * @author sunny
 * @version 1.0.0
 * @since 2012-7-23
 */
@SuppressWarnings(value = "serial,unused")
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

}
