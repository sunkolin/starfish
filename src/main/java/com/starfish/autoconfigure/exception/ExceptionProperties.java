package com.starfish.autoconfigure.exception;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * ExceptionProperties
 *
 * @author suncolin
 * @version 1.0.0
 * @since 2021-06-08
 */
@Data
@ConfigurationProperties(prefix = "application.exception")
public class ExceptionProperties {

    /**
     * 是否启用,true启用，false禁用
     */
    private Boolean enabled = true;

}
