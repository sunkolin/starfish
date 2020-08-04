package com.starfish.extension.spring.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * WebConfig
 *
 * @author sunny
 * @version 1.0.0
 * @since 2019-07-04
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Resource
    private TimeInterceptor timeInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(timeInterceptor).addPathPatterns("/**").excludePathPatterns("/login", "/register");
    }

}
