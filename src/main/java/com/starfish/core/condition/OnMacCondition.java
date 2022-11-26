package com.starfish.core.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * OnMacCondition
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2021-06-08
 */
public class OnMacCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Environment environment = context.getEnvironment();
        String osName = environment.getProperty("os.name");
        return "Mac OS X".equalsIgnoreCase(osName);
    }

}
