package com.starfish.common.executor;

import com.google.common.base.Strings;
import com.starfish.common.trace.TraceTaskDecorator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import jakarta.annotation.Resource;
import java.util.concurrent.RejectedExecutionHandler;

/**
 * 线程池配置
 * 在springboot2.1.0版本之后已加提供类似功能，故此功能废弃
 * 如果需要加载此组件，需要在spring.factories文件中增加如下内容
 * org.springframework.boot.autoconfigure.EnableAutoConfiguration=com.starfish.common.executor.ExecutorAutoConfiguration,\
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2015-08-03
 * @deprecated since 2022-08-01
 */
@Deprecated
@Slf4j
@Configuration
@AutoConfigureAfter(TaskExecutionAutoConfiguration.class)
@ConditionalOnProperty(prefix = "starfish.executor", name = "enabled", havingValue = "true")
@EnableConfigurationProperties({ExecutorProperties.class})
public class ExecutorAutoConfiguration {

    @Resource
    private ExecutorProperties executorProperties;

    @Bean(name = {"executor"}, initMethod = "afterPropertiesSet", destroyMethod = "destroy")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor(ObjectProvider<TraceTaskDecorator> taskDecorator) {
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

            TraceTaskDecorator traceTaskDecorator = taskDecorator.getIfUnique();
            if (traceTaskDecorator !=null){
                threadPoolTaskExecutor.setTaskDecorator(traceTaskDecorator);
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
