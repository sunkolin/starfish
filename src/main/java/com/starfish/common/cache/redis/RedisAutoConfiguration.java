package com.starfish.common.cache.redis;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * RedisServiceAutoConfiguration
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2026-04-22
 */
@Configuration
@ConditionalOnClass(RedisTemplate.class)
@ConditionalOnBean(RedisTemplate.class)
@ConditionalOnProperty(prefix = "spring.data.redis", name = "host")
public class RedisAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public RedisService redisService(RedisTemplate<Object, Object> redisTemplate) {
        return new RedisService(redisTemplate);
    }

}
