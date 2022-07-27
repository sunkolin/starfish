package com.starfish.common.trace;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * TraceInterceptor
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2018-12-05
 */
@Slf4j
public class TraceInterceptor implements HandlerInterceptor {

//    private final Tracer sleuthTracer;
//
//    @Autowired(required = false)
//    public TraceInterceptor(Tracer sleuthTracer) {
//        this.sleuthTracer = sleuthTracer;
//    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
       Trace.setTraceId();
//        if (sleuthTracer != null) {
//            try {
//                // 新的方式获取traceId
//                sleuthTracer.currentSpan().context().traceId();
//                // 设置Header
//                response.setHeader(TRACE_ID_NAME, traceId);
//            } catch (Exception e) {
//                log.error("TraceIdInterceptor get trace id error.", e);
//            }
//        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //
    }

}
