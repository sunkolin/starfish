package com.starfish.configuration;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

/**
 * RedisConfig
 *
 * @author sunny
 * @version 1.0.0
 * @since 2015-06-26
 */
@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.database}")
    private int database;

    @Value("${spring.redis.password}")
    private String password;

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory factory = new JedisConnectionFactory();

        factory.setHostName(host);
        factory.setPort(port);
        factory.setPassword(password);
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(2000);
        config.setMaxIdle(100);
        config.setMaxWaitMillis(3000);
        config.setTestOnBorrow(true);
        factory.setPoolConfig(config);
        factory.setUsePool(true);
        factory.setDatabase(database);
        return factory;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        GenericFastJsonRedisSerializer genericFastJsonRedisSerializer = new GenericFastJsonRedisSerializer();

        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());

        redisTemplate.setValueSerializer(genericFastJsonRedisSerializer);
        redisTemplate.setHashValueSerializer(genericFastJsonRedisSerializer);

        return redisTemplate;
    }

//    @Bean
//    public RedisCacheManager cacheManager() {
//        return new RedisCacheManager(redisTemplate());
//    }

}
