package com.starfish.common.push.bark;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * BarkProperties
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2026-01-07
 */
@Data
@ConfigurationProperties(prefix = "starfish.bark")
public class BarkProperties {

    private boolean enabled = false;

    private String baseUrl = "https://bark.day.app";

    private String messagePushUrl = "/push";

}
