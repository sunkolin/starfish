package com.starfish.core.condition;

import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

/**
 * ConditionalOnDevProfile
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2025-12-25
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(OnProfileCondition.class)
public @interface ConditionalOnProfile {

	String[] value();

}
