package com.starfish.core.exception;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;

/**
 * ExceptionAutoConfiguration
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2021-06-04
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(HandlerExceptionResolver.class)
@EnableConfigurationProperties(ExceptionProperties.class)
@ConditionalOnProperty(prefix = "starfish.exception", name = "enabled", havingValue = "true", matchIfMissing = true)
public class ExceptionAutoConfiguration {

    /**
     * 创建ExceptionResolver对象
     *
     * @return 结果
     */
    @Bean
    public DefaultExceptionResolver defaultExceptionResolver() {
        return new DefaultExceptionResolver();
    }

}
