package com.starfish.core.web;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * RequestLogInterceptorConfig
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2024-08-23
 */
public class RequestLogInterceptorConfig implements WebMvcConfigurer {

    private final RequestLogInterceptor requestLogInterceptor;

    public RequestLogInterceptorConfig(RequestLogInterceptor requestLogInterceptor) {
        this.requestLogInterceptor = requestLogInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestLogInterceptor).addPathPatterns("/**").excludePathPatterns("/excludeUrl");
    }

}
