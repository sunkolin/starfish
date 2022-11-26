package com.starfish.core.condition;

import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * OnWindowsCondition
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2021-06-08
 */
public class OnWindowsCondition extends SpringBootCondition {

    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Environment environment = context.getEnvironment();
        String osName = environment.getProperty("os.name");
        boolean match = (osName != null && osName.contains("Windows"));
        String message = "osName=" + osName;
        return new ConditionOutcome(match, message);
    }

}
