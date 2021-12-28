package com.starfish.model;

import com.starfish.enumeration.ResultEnum;
import com.starfish.exception.CustomException;

import java.io.Serializable;

/**
 * ApiResult
 *
 * @author neacle
 * @version 1.0.0
 * @since 2020-10-30
 */
public class ApiResult<T> extends Result<T> implements Serializable {

    public ApiResult() {
        super();
    }

    public ApiResult(T body) {
        super(body);
    }

    public ApiResult(ResultEnum resultEnum) {
        super(resultEnum);
    }

    public ApiResult(CustomException exception) {
        super(exception);
    }

    public ApiResult(Integer status, String message) {
        super(status, message);
    }

}
