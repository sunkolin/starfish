package com.sunny.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * 文件上传配置
 *
 * @author sunny
 * @version 1.0.0
 * @since 2015-08-04
 */
@Deprecated
@Configuration
public class UploadConfiguration {

    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setDefaultEncoding("UTF-8");
        resolver.setMaxUploadSize(1048576);
        resolver.setResolveLazily(false);
        resolver.setMaxInMemorySize(4096);
        return resolver;
    }

}
