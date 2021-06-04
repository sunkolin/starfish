package com.starfish.exception;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * ExceptionResolverAutoConfiguration
 *
 * @author sunny
 * @version 1.0.0
 * @since 2021-06-04
 */
@ComponentScan(basePackages = {"com.starfish"}, excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.starfish.trial.*"))
public class ExceptionResolverAutoConfiguration {

}
