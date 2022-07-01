package com.starfish.autoconfigure.web;

import com.starfish.autoconfigure.swagger.SwaggerAutoConfiguration;
import com.starfish.autoconfigure.swagger.SwaggerInterceptor;
import com.starfish.interceptor.TimeInterceptor;
import com.starfish.interceptor.TraceInterceptor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * WebAutoConfiguration
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2019-07-04
 */
@AutoConfiguration(after = {SwaggerAutoConfiguration.class, TimeInterceptor.class, TraceInterceptor.class, SwaggerInterceptor.class})
public class WebAutoConfiguration implements WebMvcConfigurer {

    /**
     * 此处对象不要new，否则会导致拦截器中依赖的对象为null
     */
    @Resource
    private TraceInterceptor traceInterceptor;

    @Resource
    private TimeInterceptor timeInterceptor;

    @Resource
    private SwaggerInterceptor swaggerInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (traceInterceptor != null) {
            registry.addInterceptor(traceInterceptor).addPathPatterns("/**");
        }
        if (timeInterceptor != null) {
            registry.addInterceptor(timeInterceptor).addPathPatterns("/**").excludePathPatterns("/excludeUrl");
        }
        if (swaggerInterceptor != null) {
            registry.addInterceptor(swaggerInterceptor).addPathPatterns("/swagger-ui.html");
        }
    }

}
