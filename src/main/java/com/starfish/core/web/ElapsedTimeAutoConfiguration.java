package com.starfish.core.web;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * ElapsedTimeAutoConfiguration
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2022-07-04
 */
@AutoConfiguration
@ConditionalOnProperty(value = {"starfish.elapsed-time.enabled"}, havingValue = "true", matchIfMissing = false)
@EnableConfigurationProperties({ElapsedTimeProperties.class})
public class ElapsedTimeAutoConfiguration {

    @Bean
    public ElapsedTimeInterceptor createElapsedTimeInterceptor() {
        return new ElapsedTimeInterceptor();
    }

    @Bean("elapsedTimeConfigurer")
    public WebMvcConfigurer traceWebMvcConfigurer(HandlerInterceptor elapsedTimeInterceptor) {
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(elapsedTimeInterceptor)
                        .addPathPatterns("/**");
            }
        };
    }

}
