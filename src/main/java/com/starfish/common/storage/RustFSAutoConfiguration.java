package com.starfish.common.storage;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.net.URI;

/**
 * RustFSAutoConfiguration
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2026-11-19
 */
@AutoConfiguration
@ConditionalOnProperty(value = {"starfish.rustfs.enabled"}, havingValue = "true")
@EnableConfigurationProperties({RustFSProperties.class})
public class RustFSAutoConfiguration {

    @Bean("rustFSService")
    public RustFSService rustFSService(RustFSProperties rustFSProperties) {
        return new RustFSService(rustFSProperties);
    }

}
