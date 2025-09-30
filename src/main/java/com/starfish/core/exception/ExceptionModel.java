package com.starfish.core.exception;

/**
 * ExceptionModel
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2025-09-30
 */
public class ExceptionModel {

    private final Integer code;

    private final String message;

    public ExceptionModel(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
