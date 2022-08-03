package com.starfish.core.model;

import java.io.Serializable;

/**
 * ApiResult
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2020-10-30
 */
public class ApiResult<T> extends Result<T> implements Serializable {

    public ApiResult() {
        super();
    }

}
