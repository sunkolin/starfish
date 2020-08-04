package com.sunny.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.Properties;
import java.util.concurrent.RejectedExecutionHandler;

/**
 * QuartzConfiguration
 *
 * @author sunny
 * @version 1.0.0
 * @since 2015-09-07
 */
@Deprecated
@Configuration
@EnableScheduling
//@ImportResource("classpath:/spring/spring-default-quartz.xml")
public class QuartzConfiguration {

    private static Logger logger = LoggerFactory.getLogger(QuartzConfiguration.class);

    @Autowired
    @Qualifier("defaultProperties")
    private Properties defaultProperties;

    @Bean
    public ThreadPoolTaskScheduler taskScheduler() {
        try {
            Integer corePoolSize = Integer.valueOf(defaultProperties.getProperty("thread_pool_core_pool_size"));
//            Integer maxPoolSize = Integer.valueOf(defaultProperties.getProperty("thread_pool_max_pool_size"));
//            Integer queueCapacity = Integer.valueOf(defaultProperties.getProperty("thread_pool_queue_capacity"));
//            Integer keepAliveSeconds = Integer.valueOf(defaultProperties.getProperty("thread_pool_keep_alive_seconds"));
            String rejectedExecutionHandlerClassName = defaultProperties.getProperty("thread_pool_rejected_execution_handler");
            RejectedExecutionHandler rejectedExecutionHandler = (RejectedExecutionHandler) Class.forName(rejectedExecutionHandlerClassName).newInstance();

            ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
            scheduler.setPoolSize(corePoolSize);

            scheduler.setRejectedExecutionHandler(rejectedExecutionHandler);
            scheduler.setThreadNamePrefix("task-");
            scheduler.setWaitForTasksToCompleteOnShutdown(false);
            return scheduler;
        } catch (Exception e) {
            logger.error("could not configure ThreadPoolTaskScheduler");
            return null;
        }
    }

}
