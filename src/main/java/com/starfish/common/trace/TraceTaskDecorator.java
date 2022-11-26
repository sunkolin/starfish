package com.starfish.common.trace;

import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;

import java.util.Map;

/**
 * TraceTaskDecorator
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2022-08-15
 */
public class TraceTaskDecorator implements TaskDecorator {

    @Override
    public Runnable decorate(Runnable runnable) {
        Map<String, String> map = MDC.getCopyOfContextMap();
        return () -> {
            try {
                MDC.setContextMap(map);
                runnable.run();
            } finally {
                MDC.clear();
            }
        };
    }

}
