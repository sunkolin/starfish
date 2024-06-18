package com.starfish.core.web;

import com.starfish.common.trace.TraceInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebConfig
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2019-07-04
 */
public class WebConfig implements WebMvcConfigurer {

    /**
     * 此处对象不要new，否则会导致拦截器中依赖的对象为null
     */
    private TraceInterceptor traceInterceptor;

    private ElapsedTimeInterceptor elapsedTimeInterceptor;

    public WebConfig(TraceInterceptor traceInterceptor, ElapsedTimeInterceptor elapsedTimeInterceptor) {
        this.traceInterceptor = traceInterceptor;
        this.elapsedTimeInterceptor = elapsedTimeInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (traceInterceptor != null) {
            registry.addInterceptor(traceInterceptor).addPathPatterns("/**");
        }
        if (elapsedTimeInterceptor != null) {
            registry.addInterceptor(elapsedTimeInterceptor).addPathPatterns("/**").excludePathPatterns("/excludeUrl");
        }
    }

}
