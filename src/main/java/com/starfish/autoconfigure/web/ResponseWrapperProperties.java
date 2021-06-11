package com.starfish.autoconfigure.web;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * ResponseWrapperProperties
 *
 * @author sunny
 * @version 1.0.0
 * @since 2021-06-08
 */
@Data
@ConfigurationProperties(prefix = "application.web.response-wrapper")
public class ResponseWrapperProperties {

    /**
     * 是否启用,true启用，false禁用
     */
    private Boolean enabled = true;

}
