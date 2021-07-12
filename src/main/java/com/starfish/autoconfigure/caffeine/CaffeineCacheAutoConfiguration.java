package com.starfish.autoconfigure.caffeine;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.starfish.extension.cache.CachePlus;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * CaffeineCacheAutoConfiguration
 *
 * @author sunny
 * @version 1.0.0
 * @since 2021-06-10
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({Caffeine.class})
@Conditional({CaffeineCacheCondition.class})
@EnableConfigurationProperties({CaffeineCacheProperties.class})
@EnableCaching
public class CaffeineCacheAutoConfiguration {

    @Resource
    private CaffeineCacheProperties caffeineCacheProperties;

    /**
     * 默认一小时过期
     */
    public static final long EXPIRE_TIME = 60 * 60L;

    @Bean(name = "caffeine")
    public Caffeine<Object, Object> newCaffeine() {
        Caffeine<Object, Object> caffeine = Caffeine.newBuilder();
        caffeine.expireAfterWrite(caffeineCacheProperties.getExpire(), TimeUnit.SECONDS);
        if (caffeineCacheProperties.getWeakKeys()) {
            caffeine.weakKeys();
        }
        if (caffeineCacheProperties.getWeakValues()) {
            caffeine.weakValues();
        }
        if (caffeineCacheProperties.getSoftValues()) {
            caffeine.softValues();
        }
        return caffeine;
    }

    @Bean(name = "caffeineCache")
    public Cache<Object, Object> newCaffeineCache(Caffeine<Object, Object> caffeine) {
        return caffeine.build();
    }

    @Bean(name = "cachePlus")
    public CachePlus newCaffeineCachePlus(Cache<Object, Object> caffeineCache) {
        return new CachePlus(caffeineCache);
    }

    @Bean(name = "caffeineCacheManager")
    public CacheManager cacheManager(Caffeine<Object, Object> caffeine) {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(caffeine);
        return cacheManager;
    }

}