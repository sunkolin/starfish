package com.starfish.autoconfigure.caffeine;

import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * CaffeineCacheCondition
 *
 * @author suncolin
 * @version 1.0.0
 * @since 2021-06-11
 */
public class CaffeineCacheCondition extends SpringBootCondition {

    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Environment environment = context.getEnvironment();
        Boolean cacheEnabled = environment.getProperty("application.cache.caffeine.enabled", Boolean.class);
        if (cacheEnabled != null && cacheEnabled) {
            return ConditionOutcome.match("application.cache.caffeine.enabled=true");
        } else {
            return ConditionOutcome.noMatch("application.cache.caffeine.enabled=false");
        }
    }

}
