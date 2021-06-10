package com.starfish.autoconfigure.web;

import com.starfish.exception.ExceptionResolver;
import com.starfish.web.wrapper.ResponseWrapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ResponseWrapperAutoConfiguration
 * 默认不启用
 *
 * @author sunny
 * @version 1.0.0
 * @since 2021-06-04
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(ResponseWrapperProperties.class)
@ConditionalOnProperty(prefix = "application.web.response-wrapper", name = "enabled", havingValue = "true", matchIfMissing = true)
public class ResponseWrapperAutoConfiguration {

    /**
     * 创建ResponseWrapper对象
     *
     * @return 结果
     */
    @Bean
    public ResponseWrapper newResponseWrapper() {
        return new ResponseWrapper();
    }

}
