package com.starfish.core.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * TimeBasedResult
 *
 * @author sunny
 * @version 1.0.0
 * @since 2019-07-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TimeBasedResult<T> extends Result<T> implements Serializable {

    /**
     * 请求时间
     */
    private long requestTime;

    /**
     * 请求耗时，单位毫秒
     */
    private long elapsedTime;

}
