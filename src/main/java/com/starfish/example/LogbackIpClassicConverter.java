package com.starfish.example;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;

/**
 * IpClassicConverter
 * <a href="https://www.freesion.com/article/9551563594/">LOGBACK日志名和日志内容配置增加IP等信息</a>
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2020-09-13
 */
@Slf4j
public class LogbackIpClassicConverter extends ClassicConverter {

    @Override
    public String convert(ILoggingEvent iLoggingEvent) {
        String ip = null;
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            log.error("IpClassicConverter exception.", e);
        }
        return ip;
    }

}
