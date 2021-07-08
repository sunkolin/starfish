package com.starfish.core.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页结果
 *
 * @author sunny
 * @version 1.0.0
 * @since 2015-04-13
 */
@Data
@SuppressWarnings(value = "unused")
public class PageResult<T> implements Serializable {

    /**
     * total count，optional
     */
    private Long totalCount;

    /**
     * list
     */
    private List<T> list;

}
