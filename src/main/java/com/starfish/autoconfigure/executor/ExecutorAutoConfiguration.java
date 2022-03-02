package com.starfish.autoconfigure.executor;

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
 * 在springboot2.1.0版本之后已加提供类似功能
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2015-08-03
 */
@Deprecated
@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "application.executor", name = "enabled", havingValue = "true", matchIfMissing = true)
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
