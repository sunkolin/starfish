package com.starfish.autoconfigure.trace;

import com.starfish.extension.trace.TraceConfigurer;
import com.starfish.extension.trace.TraceInterceptor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * TraceAutoConfiguration
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2022-07-03
 */
@AutoConfiguration
@ConditionalOnProperty(value = {"application.trace.enabled"}, matchIfMissing = true)
@EnableConfigurationProperties({TraceProperties.class})
public class TraceAutoConfiguration {

    @Bean
    public TraceInterceptor createTraceInterceptor() {
        return new TraceInterceptor();
    }

    @Bean
    public TraceConfigurer createTraceConfigurer(TraceInterceptor traceInterceptor) {
        return new TraceConfigurer(traceInterceptor);
    }

}
