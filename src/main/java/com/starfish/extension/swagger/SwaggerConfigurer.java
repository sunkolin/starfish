package com.starfish.extension.swagger;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * SwaggerConfigurer
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2022-07-04
 */
public class SwaggerConfigurer implements WebMvcConfigurer {

    private SwaggerInterceptor swaggerInterceptor;

    public SwaggerConfigurer(SwaggerInterceptor swaggerInterceptor) {
        this.swaggerInterceptor = swaggerInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (swaggerInterceptor != null) {
            registry.addInterceptor(swaggerInterceptor).addPathPatterns("/swagger-ui.html");
        }
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (swaggerInterceptor != null) {
            registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
            registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
            registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
            registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        }
    }

}
