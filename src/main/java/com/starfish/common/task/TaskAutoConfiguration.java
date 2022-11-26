package com.starfish.common.task;

import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * XxlJobAutoConfiguration
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2015-08-03
 */
@Slf4j
@AutoConfiguration
@ConditionalOnProperty(prefix = "xxl.job", name = "enabled", havingValue = "true")
@EnableConfigurationProperties({TaskProperties.class})
public class TaskAutoConfiguration {

    @Autowired
    private TaskProperties properties;

    @Bean
    public XxlJobSpringExecutor xxlJobExecutor() {
        log.info(">>>>>>>>>>> xxl-job config init.");
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        TaskProperties.Admin admin = properties.getAdmin();
        TaskProperties.Executor executor = properties.getExecutor();
        xxlJobSpringExecutor.setAdminAddresses(admin.getAddresses());
        xxlJobSpringExecutor.setAddress(executor.getAddress());
        xxlJobSpringExecutor.setAppname(executor.getAppname());
        xxlJobSpringExecutor.setIp(executor.getIp());
        xxlJobSpringExecutor.setPort(executor.getPort());
        xxlJobSpringExecutor.setAccessToken(properties.getAccessToken());
        xxlJobSpringExecutor.setLogPath(executor.getLogpath());
        xxlJobSpringExecutor.setLogRetentionDays(executor.getLogretentiondays());
        log.info(">>>>>>>>>>> xxl-job config init complete.");
        return xxlJobSpringExecutor;
    }

}
