package com.starfish.core.web;

import com.starfish.core.controller.AliveController;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

/**
 * AliveAutoConfiguration
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2022-07-03
 */
@AutoConfiguration
@ConditionalOnProperty(prefix = "starfish.alive", name = "enabled", havingValue = "true", matchIfMissing = true)
public class AliveAutoConfiguration {

    @Bean
    public AliveController createAliveController() {
        return new AliveController();
    }

}
