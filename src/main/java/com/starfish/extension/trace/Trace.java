package com.starfish.extension.trace;

import com.starfish.util.StringUtil;
import org.slf4j.MDC;

/**
 * Trace
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2022-07-04
 */
public class Trace {

    public static final String TRACE_ID_NAME = "traceId";

    /**
     * set trace id
     */
    public static void setTraceId() {
        String traceId = MDC.get(TRACE_ID_NAME);
        // 如果当前没有traceId，则设置
        if (traceId == null || traceId.length() <= 0) {
            traceId = StringUtil.random(16);
            MDC.put(TRACE_ID_NAME, traceId);
        }
    }

    /**
     * get trace id
     *
     * @return trace id
     */
    public static String getTraceId() {
        String traceId = MDC.get(TRACE_ID_NAME);
        if (traceId == null || traceId.length() <= 0) {
            traceId = StringUtil.random(16);
            MDC.put(TRACE_ID_NAME, traceId);
        }
        return traceId;
    }

}
