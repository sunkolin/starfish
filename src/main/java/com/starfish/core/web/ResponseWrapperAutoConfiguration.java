package com.starfish.core.web;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * ResponseWrapperAutoConfiguration
 * 默认不启用
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2021-06-04
 */
@AutoConfiguration
@EnableConfigurationProperties(ResponseWrapperProperties.class)
@ConditionalOnProperty(prefix = "application.web.response-wrapper", name = "enabled", havingValue = "true")
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
