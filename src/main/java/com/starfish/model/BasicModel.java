package com.starfish.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import com.starfish.constant.Constant;
import com.starfish.model.jsonview.DefaultJsonView;
import com.starfish.model.jsonview.ExtJsonView;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * BasicModel
 *
 * @author sunny
 * @version 1.0.0
 * @since 2013-3-11
 */
@Data
@SuppressWarnings(value = "unused")
public class BasicModel implements Serializable {

    /**
     * 创建人
     */
    @JsonView(DefaultJsonView.class)
    private String creator;

    /**
     * 创建时间
     */
    @JsonView(DefaultJsonView.class)
    @JSONField(format = Constant.DATE_TIME_PATTERN)
    @DateTimeFormat(pattern = Constant.DATE_TIME_PATTERN)
    @JsonFormat(pattern = Constant.DATE_TIME_PATTERN, timezone = "GMT+8")
    private Date createTime;

    /**
     * 最后修改人
     */
    @JsonView(DefaultJsonView.class)
    private String modifier;

    /**
     * 最后修改时间
     */
    @JsonView(DefaultJsonView.class)
    @JSONField(format = Constant.DATE_TIME_PATTERN)
    @DateTimeFormat(pattern = Constant.DATE_TIME_PATTERN)
    @JsonFormat(pattern = Constant.DATE_TIME_PATTERN, timezone = "GMT+8")
    private Date modifyTime;

    /**
     * 删除状态
     */
    @JsonView(ExtJsonView.class)
    private String deleteStatus;

}
