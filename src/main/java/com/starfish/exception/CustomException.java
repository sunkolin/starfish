package com.starfish.exception;

import com.starfish.enumeration.ResultEnum;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * @author sunny
 * @version 1.0.0
 * @since 2013-5-7
 */
@Slf4j
@SuppressWarnings(value = "unused")
public class CustomException extends RuntimeException implements Serializable {

    /**
     * code
     */
    public int code = 0;

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

    public CustomException(int code) {
        this(code, ResultEnum.SYSTEM_EXCEPTION.getMessage(), ResultEnum.SYSTEM_EXCEPTION.getMessage());
    }

    public CustomException(String message) {
        this(ResultEnum.SYSTEM_EXCEPTION.getCode(), message, message);
    }

    /**
     * construct method
     *
     * @param code    code
     * @param message message
     */
    public CustomException(int code, String message) {
        this(code, message, message);
    }

    /**
     * 构造方法
     *
     * @param code        code
     * @param message     message
     * @param description description
     */
    public CustomException(int code, String message, String description) {
        super(message);
        this.code = code;
        this.message = message;
        this.description = description;
    }

    /**
     * 构造方法
     *
     * @param resultEnum resultEnum
     */
    public CustomException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
        this.message = resultEnum.getMessage();
        this.description = resultEnum.getMessage();
    }

    /**
     * 构造方法，对象需要有getCode和getMessage方法
     *
     * @param object object
     */
    public CustomException(Object object) {
        try {
            Method getCodeMethod = object.getClass().getMethod("getCode");
            Method getMessageMethod = object.getClass().getMethod("getMessage");
            Object codeObject = getCodeMethod.invoke(object);
            Object messageObject = getMessageMethod.invoke(object);
            int code = (codeObject == null ? -1 : (int) codeObject);
            String message = (messageObject == null ? "" : messageObject.toString());
            this.code = code;
            this.message = message;
            this.description = message;
        } catch (Exception e) {
            log.error("CustomException", e);
        }
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
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
