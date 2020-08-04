package com.sunny.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import com.sunny.constant.Constant;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * general model
 *
 * @author sunny
 * @version 1.0.0
 * @since 2013-3-11
 */
@SuppressWarnings(value = "serial,unused")
public class BasicModel implements Serializable {

    /**
     * 删除状态
     */
    @JsonView(ExtraJsonView.class)
    private String deleteStatus;

    /**
     * 操作人
     */
    @JsonView(CommonJsonView.class)
    private String operator;

    /**
     * 操作时间
     */
    @JsonView(CommonJsonView.class)
    @JSONField(format = Constant.DATE_TIME_PATTERN)
    @DateTimeFormat(pattern = Constant.DATE_TIME_PATTERN)
    @JsonFormat(pattern = Constant.DATE_TIME_PATTERN, timezone = "GMT+8")
    private Date operateTime;

    /**
     * 创建人
     */
    @JsonView(ExtraJsonView.class)
    private String creator;

    /**
     * 创建时间
     */
    @JsonView(ExtraJsonView.class)
    @JSONField(format = Constant.DATE_TIME_PATTERN)
    @DateTimeFormat(pattern = Constant.DATE_TIME_PATTERN)
    @JsonFormat(pattern = Constant.DATE_TIME_PATTERN, timezone = "GMT+8")
    private Date createTime;

    /**
     * 普通显示，前台
     */
    public interface CommonJsonView {
    }

    /**
     * 额外显示，前台
     */
    public interface ExtraJsonView {
    }

    /**
     * 显示所有，后台使用
     */
    public interface TotalJsonView extends CommonJsonView, ExtraJsonView {
    }

    public String getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(String deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

}
