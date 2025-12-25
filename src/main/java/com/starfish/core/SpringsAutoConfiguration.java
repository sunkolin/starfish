package com.starfish.core;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * SpringsAutoConfiguration
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2025-12-24
 */
@AutoConfiguration
@ConditionalOnMissingBean(Springs.class)
public class SpringsAutoConfiguration {

    @Bean
    public Springs springs() {
        return new Springs();
    }

}
