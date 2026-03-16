package com.starfish.core.datasource;

import lombok.Data;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;
import org.springframework.boot.jdbc.autoconfigure.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.jdbc.autoconfigure.JdbcTemplateAutoConfiguration;
import org.springframework.context.annotation.Import;

/**
 * StarfishDataSourceProperties
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2026-03-16
 */
@Data
@ConfigurationProperties(prefix = "starfish.datasource")
public class StarfishDataSourceProperties {

    /**
     * 是否启用,true启用，false禁用，默认启用
     */
    private Boolean enabled = true;

}