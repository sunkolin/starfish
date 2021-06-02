package com.starfish.config;

import com.starfish.interceptor.SwaggerInterceptor;
import com.starfish.interceptor.TimeInterceptor;
import com.starfish.interceptor.TraceIdInterceptor;
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
public class DefaultWebMvcConfig implements WebMvcConfigurer {

    @Resource
    private TimeInterceptor timeInterceptor;

    @Resource
    private SwaggerInterceptor swaggerInterceptor;

    /**
     * 此处对象不要new，否则会导致拦截器中依赖的对象为null
     */
    @Resource
    private TraceIdInterceptor traceIdInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(traceIdInterceptor).addPathPatterns("/**").excludePathPatterns("/login", "/register");
        registry.addInterceptor(timeInterceptor).addPathPatterns("/**").excludePathPatterns("/login", "/register");
        registry.addInterceptor(swaggerInterceptor).addPathPatterns("/swagger-ui.html");
    }

}
