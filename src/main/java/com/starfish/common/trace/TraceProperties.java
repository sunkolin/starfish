package com.starfish.common.trace;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * TraceProperties
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2022-06-08
 */
@Data
@ConfigurationProperties(prefix = "application.trace")
public class TraceProperties {

    /**
     * 是否启用,true启用，false禁用
     */
    private Boolean enabled = false;

}
