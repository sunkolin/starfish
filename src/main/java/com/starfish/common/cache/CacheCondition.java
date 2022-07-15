package com.starfish.common.cache;

import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * CaffeineCacheCondition
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2021-06-11
 */
public class CacheCondition extends SpringBootCondition {

    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Environment environment = context.getEnvironment();
        Boolean cacheEnabled = environment.getProperty("application.cache.enabled", Boolean.class);
        if (cacheEnabled != null && cacheEnabled) {
            return ConditionOutcome.match("application.cache.enabled=true");
        } else {
            return ConditionOutcome.noMatch("application.cache.enabled=false");
        }
    }

}
