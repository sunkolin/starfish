package com.starfish.extension.time;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * ElapsedTimeConfigurer
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2022-07-04
 */
public class ElapsedTimeConfigurer implements WebMvcConfigurer {

    private ElapsedTimeInterceptor elapsedTimeInterceptor;

    public ElapsedTimeConfigurer(ElapsedTimeInterceptor elapsedTimeInterceptor) {
        this.elapsedTimeInterceptor = elapsedTimeInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (elapsedTimeInterceptor != null) {
            registry.addInterceptor(elapsedTimeInterceptor).addPathPatterns("/**").excludePathPatterns("/excludeUrl");
        }
    }

}
