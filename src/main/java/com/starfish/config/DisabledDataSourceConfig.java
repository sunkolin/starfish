package com.starfish.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * DisableDataSource
 *
 * @author sunny
 * @version 1.0.0
 * @since 2021-06-02
 */
//@ConditionalOnExpression("${spring.datasource.enabled:true} || ${application.datasource.enabled:true}")
@ConditionalOnExpression("${application.datasource.disabled:false}")
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
//@ComponentScan(excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = DataSourceAutoConfiguration.class)})
public class DisabledDataSourceConfig {

}
