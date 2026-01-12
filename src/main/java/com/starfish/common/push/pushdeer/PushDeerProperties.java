package com.starfish.common.push.pushdeer;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * PushDeerProperties
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2026-01-06
 */
@Data
@ConfigurationProperties(prefix = "starfish.pushdeer")
public class PushDeerProperties {

    private boolean enabled = false;

    private String baseUrl = "https://api2.pushdeer.com";

    private String messagePushUrl = "/message/push";

}
