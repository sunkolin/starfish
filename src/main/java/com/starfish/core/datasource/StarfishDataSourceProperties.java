package com.starfish.core.datasource;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

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
