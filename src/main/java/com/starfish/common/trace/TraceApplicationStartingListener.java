package com.starfish.common.trace;

import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;

/**
 * TraceApplicationStartingListener
 * spring boot 启动后，设置一个trace_id
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2025-12-26
 */
public class TraceApplicationStartingListener implements ApplicationListener<ApplicationStartingEvent> {

    @Override
    public void onApplicationEvent(ApplicationStartingEvent event) {
        Trace.setTraceId();
    }

}
