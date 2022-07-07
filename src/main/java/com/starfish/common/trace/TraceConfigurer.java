package com.starfish.common.trace;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * TraceConfigurer
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2022-07-04
 */
public class TraceConfigurer implements WebMvcConfigurer {

    private TraceInterceptor traceInterceptor;

    public TraceConfigurer(TraceInterceptor traceInterceptor) {
        this.traceInterceptor = traceInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (traceInterceptor != null) {
            registry.addInterceptor(traceInterceptor).addPathPatterns("/**");
        }
    }

}
