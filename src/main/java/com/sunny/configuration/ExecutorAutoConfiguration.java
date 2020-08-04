package com.sunny.configuration;

import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;
import java.util.concurrent.RejectedExecutionHandler;

/**
 * 线程池配置
 *
 * @author sunny
 * @version 1.0.0
 * @since 2015-08-03
 */
@Slf4j
@Configuration
@ConditionalOnProperty(value = {"application.executor.enabled"}, matchIfMissing = true)
@EnableConfigurationProperties({ExecutorProperties.class})
public class ExecutorAutoConfiguration {

    @Resource
    private ExecutorProperties executorProperties;

    @Bean(name = {"executor", "threadPoolTaskExecutor"}, initMethod = "afterPropertiesSet", destroyMethod = "destroy")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        try {
            ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();

            Integer corePoolSize = executorProperties.getCorePoolSize();
            Integer maxPoolSize = executorProperties.getMaxPoolSize();
            Integer queueCapacity = executorProperties.getQueueCapacity();
            Integer keepAliveSeconds = executorProperties.getKeepAliveSeconds();
            String rejectedExecutionHandler = executorProperties.getRejectedExecutionHandler();

            if (corePoolSize != null) {
                threadPoolTaskExecutor.setCorePoolSize(corePoolSize);
            }

            if (maxPoolSize != null) {
                threadPoolTaskExecutor.setMaxPoolSize(maxPoolSize);
            }

            if (queueCapacity != null) {
                threadPoolTaskExecutor.setQueueCapacity(queueCapacity);
            }

            if (keepAliveSeconds != null) {
                threadPoolTaskExecutor.setKeepAliveSeconds(keepAliveSeconds);
            }

            if (!Strings.isNullOrEmpty(rejectedExecutionHandler)) {
                threadPoolTaskExecutor.setRejectedExecutionHandler((RejectedExecutionHandler) Class.forName(rejectedExecutionHandler).newInstance());
            }

            return threadPoolTaskExecutor;
        } catch (Exception e) {
            log.error("could not configure thread pool");
            return null;
        }
    }

}
