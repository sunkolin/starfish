package com.starfish.core.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页结果
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2015-04-13
 */
@Data
@SuppressWarnings("unused")
public class PageResult<T> {

    /**
     * total count，optional
     */
    private Long totalCount;

    /**
     * list
     */
    private List<T> list;

}
