package com.starfish.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * ExecutorProperties
 *
 * @author sunny
 * @version 1.0.0
 * @since 2018-11-05
 */
@ConfigurationProperties(prefix = "app.executor")
public class ExecutorProperties {

    private Boolean enabled = true;

    private Integer corePoolSize;

    private Integer maxPoolSize;

    private Integer queueCapacity;

    private Integer keepAliveSeconds;

    private String rejectedExecutionHandler;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Integer getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(Integer corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public Integer getKeepAliveSeconds() {
        return keepAliveSeconds;
    }

    public void setKeepAliveSeconds(Integer keepAliveSeconds) {
        this.keepAliveSeconds = keepAliveSeconds;
    }

    public Integer getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(Integer maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public Integer getQueueCapacity() {
        return queueCapacity;
    }

    public void setQueueCapacity(Integer queueCapacity) {
        this.queueCapacity = queueCapacity;
    }

    public String getRejectedExecutionHandler() {
        return rejectedExecutionHandler;
    }

    public void setRejectedExecutionHandler(String rejectedExecutionHandler) {
        this.rejectedExecutionHandler = rejectedExecutionHandler;
    }

}
