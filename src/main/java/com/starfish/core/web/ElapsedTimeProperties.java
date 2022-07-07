package com.starfish.core.web;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * ElapsedTimeProperties
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2022-07-04
 */
@Data
@ConfigurationProperties(prefix = "application.elapsed-time")
public class ElapsedTimeProperties {

    /**
     * 是否启用,true启用，false禁用
     */
    private Boolean enabled = false;

}
