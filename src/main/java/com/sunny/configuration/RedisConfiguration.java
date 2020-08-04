package com.sunny.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

import java.util.ArrayList;
import java.util.Properties;

/**
 * RedisConfiguration
 *
 * @author sunny
 * @version 1.0.0
 * @since 2015-08-18
 */
@Deprecated
@Configuration
public class RedisConfiguration {

    @Autowired
    @Qualifier("defaultProperties")
    private Properties defaultProperties;

    @Autowired
    @Qualifier("redisPoolConfig")
    private JedisPoolConfig redisPoolConfig;

    @Bean(name = "redisPoolConfig")
    public JedisPoolConfig getRedisConfig() {
        Integer maxIdle = Integer.valueOf(defaultProperties.getProperty("redis_max_idle"));
        Boolean testOnBorrow = Boolean.valueOf(defaultProperties.getProperty("redis_test_on_borrow"));

        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(maxIdle);
        config.setTestOnBorrow(testOnBorrow);

        return config;
    }

    @Bean(name = "redisPool")
    public ShardedJedisPool redisPool() {
        ArrayList<JedisShardInfo> list = new ArrayList<JedisShardInfo>();

        String host = defaultProperties.getProperty("redis_host");
        int port = Integer.valueOf(defaultProperties.getProperty("redis_port"));
        String name = defaultProperties.getProperty("redis_name");

        JedisShardInfo info = new JedisShardInfo(host, port, name);
        list.add(info);

        return new ShardedJedisPool(redisPoolConfig, list);
    }

}
