package com.starfish.core.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * BasicParam
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2015-06-09
 */
@Data
@SuppressWarnings("unused")
@EqualsAndHashCode(callSuper = true)
public class BasicParam extends BasicModel implements Serializable {

    /**
     * 当前页数
     */
    private Integer page;

    /**
     * 偏移量，offset = count * (page - 1);
     */
    private Integer offset;

    /**
     * 查询参数 一页查询条数
     */
    private Integer count;

    /**
     * 开始时间，查询时候用到参数
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;

    /**
     * 结束时间，查询时候用到参数
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;

}
