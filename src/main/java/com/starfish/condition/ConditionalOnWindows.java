package com.starfish.condition;

import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

/**
 * ConditionalOnWindows
 *
 * @author neacle
 * @version 1.0.0
 * @since 2021-06-08
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(OnWindowsCondition.class)
public @interface ConditionalOnWindows {

	String[] value() default {};

}
