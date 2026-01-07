package com.starfish.common.push.pushdeer;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * PushDeerAutoConfiguration
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2026-01-06
 */
@AutoConfiguration
@ConditionalOnProperty(value = {"starfish.pushdeer.enabled"}, havingValue = "true")
@EnableConfigurationProperties({PushDeerProperties.class})
public class PushDeerAutoConfiguration {

    @Bean("pushDeer")
    public PushDeer createPushDeer(PushDeerProperties pushDeerProperties) {
        return new PushDeer(pushDeerProperties);
    }

}
