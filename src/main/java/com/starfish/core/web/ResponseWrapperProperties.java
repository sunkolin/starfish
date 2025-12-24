package com.starfish.core.web;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * ResponseWrapperProperties
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2021-06-08
 */
@Data
@ConfigurationProperties(prefix = "starfish.response-wrapper")
public class ResponseWrapperProperties {

    /**
     * 是否启用,true启用，false禁用
     */
    private Boolean enabled = false;

}
