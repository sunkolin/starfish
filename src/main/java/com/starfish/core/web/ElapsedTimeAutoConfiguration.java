package com.starfish.core.web;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * ElapsedTimeAutoConfiguration
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2022-07-04
 */
@AutoConfiguration
@ConditionalOnProperty(value = {"application.elapsed-time.enabled"}, matchIfMissing = true)
@EnableConfigurationProperties({ElapsedTimeProperties.class})
public class ElapsedTimeAutoConfiguration {

    @Bean
    public ElapsedTimeInterceptor createElapsedTimeInterceptor() {
        return new ElapsedTimeInterceptor();
    }

    @Bean
    public ElapsedTimeConfigurer createElapsedTimeConfigurer(ElapsedTimeInterceptor elapsedTimeInterceptor) {
        return new ElapsedTimeConfigurer(elapsedTimeInterceptor);
    }

}
