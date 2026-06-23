package com.starfish.common.cache.caffeine;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * CaffeineCacheAutoConfiguration
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2021-06-10
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({Caffeine.class})
@Conditional({CaffeineCacheCondition.class})
@EnableConfigurationProperties({CaffeineCacheProperties.class})
@EnableCaching
public class CaffeineCacheAutoConfiguration {

    @Bean(name = "caffeine")
    public Caffeine<Object, Object> newCaffeine(CaffeineCacheProperties caffeineCacheProperties) {
        Caffeine<Object, Object> caffeine = Caffeine.newBuilder();
        caffeine.expireAfterWrite(caffeineCacheProperties.getExpire(), TimeUnit.SECONDS);
        if (Boolean.TRUE.equals(caffeineCacheProperties.getWeakKeys())) {
            caffeine.weakKeys();
        }
        if (Boolean.TRUE.equals(caffeineCacheProperties.getWeakValues())) {
            caffeine.weakValues();
        }
        if (Boolean.TRUE.equals(caffeineCacheProperties.getSoftValues())) {
            caffeine.softValues();
        }
        return caffeine;
    }

    @Bean(name = "caffeineCache")
    public Cache<Object, Object> newCaffeineCache(Caffeine<Object, Object> caffeine) {
        return caffeine.build();
    }

    @Bean(name = "caffeineService")
    public CaffeineService caffeineService(Cache<Object, Object> caffeineCache) {
        return new CaffeineService(caffeineCache);
    }

    @Bean(name = "caffeineCacheManager")
    public CacheManager cacheManager(Caffeine<Object, Object> caffeine) {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(caffeine);
        return cacheManager;
    }

}