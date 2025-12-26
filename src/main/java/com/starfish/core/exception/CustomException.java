package com.starfish.core.exception;

import com.starfish.core.enumeration.ResultEnum;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * @author sunkolin
 * @version 1.0.0
 * @since 2013-06-01
 */
@Slf4j
@Getter
@EqualsAndHashCode(callSuper = true)
@SuppressWarnings("unused")
public class CustomException extends RuntimeException implements Serializable {

    /**
     * code
     */
    private final Integer code;

    /**
     * message
     */
    private final String message;

    /**
     * description
     */
    private final String description;

    public CustomException() {
        super(ResultEnum.SYSTEM_EXCEPTION.getMessage());
        this.code = ResultEnum.SYSTEM_EXCEPTION.getCode();
        this.message = ResultEnum.SYSTEM_EXCEPTION.getMessage();
        this.description = ResultEnum.SYSTEM_EXCEPTION.getMessage();
    }

    public CustomException(String message) {
        super(message);
        this.code = ResultEnum.SYSTEM_EXCEPTION.getCode();
        this.message = message;
        this.description = message;
    }

    public CustomException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
        this.description = message;
    }

    public CustomException(Integer code, String message, String description) {
        super(message);
        this.code = code;
        this.message = message;
        this.description = description;
    }

    public CustomException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
        this.message = resultEnum.getMessage();
        this.description = resultEnum.getMessage();
    }

    public CustomException(Exception exception) {
        super(exception);
        this.code = ResultEnum.SYSTEM_EXCEPTION.getCode();
        this.message = exception.getMessage();
        this.description = exception.getMessage();
    }

}