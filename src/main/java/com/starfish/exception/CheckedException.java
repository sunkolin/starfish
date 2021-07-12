package com.starfish.exception;

import java.io.Serializable;

/**
 * @author sunny
 * @version 1.0.0
 * @since 2012-9-10
 */
@SuppressWarnings(value = "unused")
public class CheckedException extends Exception implements Serializable {

    /**
     * code
     */
    public int code;

    /**
     * message
     */
    public String message = "";

    /**
     * description
     */
    public String description = "";

    public CheckedException() {

    }

    /**
     * construct method
     *
     * @param code    code
     * @param message message
     */
    public CheckedException(int code, String message) {
        this(code, message, "");
    }

    /**
     * construct method
     *
     * @param code        code
     * @param message     message
     * @param description description
     */
    public CheckedException(int code, String message, String description) {
        super();
        this.code = code;
        this.message = message;
        this.description = description;
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
