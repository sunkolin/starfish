package com.starfish.core.exception;

import com.starfish.core.enumeration.ResultEnum;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * @author sunkolin
 * @version 1.0.0
 * @since 2013-06-01
 */
@Slf4j
@SuppressWarnings("unused")
public class CustomException extends RuntimeException implements Serializable {

    /**
     * code
     */
    public Integer code;

    /**
     * message
     */
    public String message;

    /**
     * description
     */
    public String description;

    public CustomException() {
        super();
        this.code = ResultEnum.SYSTEM_EXCEPTION.getCode();
        this.message = ResultEnum.SYSTEM_EXCEPTION.getMessage();
        this.description = ResultEnum.SYSTEM_EXCEPTION.getMessage();
    }

    public CustomException(Integer code) {
        this(code, ResultEnum.SYSTEM_EXCEPTION.getMessage(), ResultEnum.SYSTEM_EXCEPTION.getMessage());
    }

    public CustomException(String message) {
        this(ResultEnum.SYSTEM_EXCEPTION.getCode(), message, message);
    }

    public CustomException(Integer code, String message) {
        this(code, message, message);
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
    }

    public CustomException(Object object) {
        try {
            Method getCodeMethod = object.getClass().getMethod("getCode");
            Method getMessageMethod = object.getClass().getMethod("getMessage");
            Object codeObject = getCodeMethod.invoke(object);
            Object messageObject = getMessageMethod.invoke(object);
            this.code = (codeObject == null ? -1 : (Integer) codeObject);
            this.message = (messageObject == null ? "" : messageObject.toString());
            this.description = message;
        } catch (Exception e) {
            log.error("CustomException", e);
        }
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
