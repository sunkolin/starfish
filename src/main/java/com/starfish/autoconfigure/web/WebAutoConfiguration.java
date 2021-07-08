package com.starfish.autoconfigure.web;

import com.starfish.autoconfigure.swagger.SwaggerInterceptor;
import com.starfish.web.interceptor.TimeInterceptor;
import com.starfish.web.interceptor.TraceIdInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * WebAutoConfiguration
 *
 * @author sunny
 * @version 1.0.0
 * @since 2019-07-04
 */
@Configuration
public class WebAutoConfiguration implements WebMvcConfigurer {

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
        if (traceIdInterceptor != null) {
            registry.addInterceptor(traceIdInterceptor).addPathPatterns("/**");
        }
        if (timeInterceptor != null) {
            registry.addInterceptor(timeInterceptor).addPathPatterns("/**").excludePathPatterns("/excludeUrl");
        }
        if (swaggerInterceptor != null) {
            registry.addInterceptor(swaggerInterceptor).addPathPatterns("/swagger-ui.html");
        }
    }

}
