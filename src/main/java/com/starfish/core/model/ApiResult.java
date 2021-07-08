package com.starfish.core.model;

import com.starfish.core.enumeration.ResultEnum;
import com.starfish.core.exception.CustomException;

import java.io.Serializable;

/**
 * ApiResult
 *
 * @author sunny
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
