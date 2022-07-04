package com.starfish.extension.web;

import com.starfish.extension.swagger.SwaggerInterceptor;
import com.starfish.extension.time.ElapsedTimeInterceptor;
import com.starfish.extension.trace.TraceInterceptor;
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

    private SwaggerInterceptor swaggerInterceptor;

    public WebConfig(TraceInterceptor traceInterceptor, ElapsedTimeInterceptor elapsedTimeInterceptor, SwaggerInterceptor swaggerInterceptor) {
        this.traceInterceptor = traceInterceptor;
        this.elapsedTimeInterceptor = elapsedTimeInterceptor;
        this.swaggerInterceptor = swaggerInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (traceInterceptor != null) {
            registry.addInterceptor(traceInterceptor).addPathPatterns("/**");
        }
        if (elapsedTimeInterceptor != null) {
            registry.addInterceptor(elapsedTimeInterceptor).addPathPatterns("/**").excludePathPatterns("/excludeUrl");
        }
        if (swaggerInterceptor != null) {
            registry.addInterceptor(swaggerInterceptor).addPathPatterns("/swagger-ui.html");
        }
    }

}
