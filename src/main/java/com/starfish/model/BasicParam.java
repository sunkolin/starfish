package com.starfish.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.starfish.constant.Constant;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * BasicParam
 *
 * @author sunny
 * @version 1.0.0
 * @since 2015-06-09
 */
public class BasicParam extends BasicModel implements Serializable {

    /**
     * 当前页数
     */
    private Integer page;

    /**
     * 偏移量
     */
    private Integer offset;

    /**
     * 查询参数 一页查询条数
     */
    private Integer count;

    /**
     * 开始时间，查询时候用到参数
     */
    @JSONField(format = Constant.DATE_TIME_PATTERN)
    @DateTimeFormat(pattern = Constant.DATE_TIME_PATTERN)
    @JsonFormat(pattern = Constant.DATE_TIME_PATTERN, timezone = "GMT+8")
    private Date startTime;

    /**
     * 结束时间，查询时候用到参数
     */
    @JSONField(format = Constant.DATE_TIME_PATTERN)
    @DateTimeFormat(pattern = Constant.DATE_TIME_PATTERN)
    @JsonFormat(pattern = Constant.DATE_TIME_PATTERN, timezone = "GMT+8")
    private Date endTime;

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
        setOffset();
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
        setOffset();
    }

    public void setOffset() {
        if (count != null && page != null) {
            offset = count * (page - 1);
        }
    }

}
