package com.starfish.common.executor;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * ExecutorProperties
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2018-11-05
 * @deprecated since 2022-08-01
 */
@Deprecated
@Data
@ConfigurationProperties(prefix = "application.executor")
public class ExecutorProperties {

    /**
     * 是否启用,true启用，false禁用
     */
    private Boolean enabled = true;

    /**
     * 核心线程数
     */
    private Integer corePoolSize = 10;

    /**
     * 最大线程数
     */
    private Integer maxPoolSize = 10;

    /**
     * 队列长度
     */
    private Integer queueCapacity = Integer.MAX_VALUE;

    /**
     * 超时回收时间
     */
    private Integer keepAliveSeconds = 60;

    /**
     * 拒绝策略
     */
    private String rejectedExecutionHandler = "java.util.concurrent.ThreadPoolExecutor$AbortPolicy";

}
