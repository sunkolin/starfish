package com.starfish.core.web;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * RequestLogProperties
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2024-08-23
 */
@Data
@ConfigurationProperties(prefix = "starfish.request-log")
public class RequestLogProperties {

    /**
     * 是否启用,true启用，false禁用
     */
    private Boolean enabled = false;

}
