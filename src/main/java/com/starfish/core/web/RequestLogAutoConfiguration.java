package com.starfish.core.web;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * RequestLogAutoConfiguration
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2022-07-04
 */
@AutoConfiguration
@ConditionalOnProperty(value = {"starfish.request-log.enabled"}, havingValue = "true", matchIfMissing = false)
@EnableConfigurationProperties({RequestLogProperties.class})
public class RequestLogAutoConfiguration {

    @Bean
    public FilterRegistrationBean<RequestWrapperFilter> requestWrapperFilter() {
        FilterRegistrationBean<RequestWrapperFilter> registrationBean = new FilterRegistrationBean<>();
        RequestWrapperFilter requestWrapperFilter = new RequestWrapperFilter();
        registrationBean.setFilter(requestWrapperFilter);
        registrationBean.setName("RequestWrapperFilter");
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }

    @Bean
    public RequestLogInterceptor createRequestLogInterceptor() {
        return new RequestLogInterceptor();
    }

    @Bean
    public WebMvcConfigurer traceWebMvcConfigurer(HandlerInterceptor requestLogInterceptor) {
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(requestLogInterceptor).addPathPatterns("/**");
            }
        };
    }

}
