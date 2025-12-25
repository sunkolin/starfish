package com.starfish.core.datasource;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;
import org.springframework.boot.jdbc.autoconfigure.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.jdbc.autoconfigure.JdbcTemplateAutoConfiguration;
import org.springframework.context.annotation.Import;

/**
 * StarfishDataSourceAutoConfiguration
 * 通过org.springframework.boot.autoconfigure.AutoConfiguration.exclude配置
 * 默认排除原生数据源配置,原生事务管理器配置,原生JdbcTemplate配置
 * 仅当开关为true时，才导入原生配置（实现条件加载）
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2025-12-24
 */
@AutoConfiguration
@ConditionalOnProperty(value = "spring.datasource.enabled", havingValue = "true")
@Import({DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class, JdbcTemplateAutoConfiguration.class})
public class StarfishDataSourceAutoConfiguration {

}