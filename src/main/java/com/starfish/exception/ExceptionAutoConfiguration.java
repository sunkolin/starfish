package com.starfish.exception;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * ExceptionResolverAutoConfiguration
 *
 * @author sunny
 * @version 1.0.0
 * @since 2021-06-04
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(ExceptionProperties.class)
@ConditionalOnProperty(prefix = "application.exception", name = "enabled", havingValue = "true", matchIfMissing = true)
//@ComponentScan(basePackages = {"com.starfish"}, excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.starfish.trial.*"))
public class ExceptionAutoConfiguration {

}
