package com.starfish.common.push.bark;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * BarkAutoConfiguration
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2026-01-06
 */
@AutoConfiguration
@ConditionalOnProperty(value = {"starfish.push.bark.enabled"}, havingValue = "true")
@EnableConfigurationProperties({BarkProperties.class})
public class BarkAutoConfiguration {

    @Bean("bark")
    public BarkPushService createPushDeer(BarkProperties barkProperties) {
        return new BarkPushService(barkProperties);
    }

}
