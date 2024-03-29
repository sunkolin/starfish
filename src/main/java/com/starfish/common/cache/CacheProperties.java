package com.starfish.common.cache;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * CaffeineCacheProperties
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2018-11-05
 */
@Data
@ConfigurationProperties(prefix = "application.cache")
public class CacheProperties {

    /**
     * 是否启用,true启用，false禁用，默认启用
     */
    private Boolean enabled = true;

    /**
     * 缓存过期时间，单位秒，默认一小时，expireAfterWrite
     */
    private Long expire = 3600L;

    /**
     * 将key设置为弱引用，在GC时可以直接淘汰，默认false
     */
    private Boolean weakKeys = false;

    /**
     * 将value设置为弱引用，在GC时可以直接淘汰，默认false
     */
    private Boolean weakValues = false;

    /**
     * 将value设置为软引用，在内存溢出前可以直接淘汰，默认false
     */
    private Boolean softValues = false;

}
