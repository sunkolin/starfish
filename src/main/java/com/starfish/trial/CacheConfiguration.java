package com.starfish.trial;

import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheFactoryBean;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * 缓存配置
 * //@Configuration
 * //@EnableCaching(proxyTargetClass = true)
 *
 * @author sunny
 * @version 1.0.0
 * @since 2015-05-22
 */
public class CacheConfiguration {

    @Resource(name = "defaultCacheFactoryBean")
    private ConcurrentMapCacheFactoryBean defaultCacheFactoryBean;

    @Resource(name = "simpleCacheFactoryBean")
    private ConcurrentMapCacheFactoryBean simpleCacheFactoryBean;

    @Resource(name = "defaultCache")
    private ConcurrentMapCache defaultCache;

    @Resource(name = "simpleCache")
    private ConcurrentMapCache simpleCache;

    @Bean(name = "defaultCacheFactoryBean", initMethod = "afterPropertiesSet")
    public ConcurrentMapCacheFactoryBean getDefaultCacheFactoryBean() {
        ConcurrentMapCacheFactoryBean defaultConcurrentMapCache = new ConcurrentMapCacheFactoryBean();
        defaultConcurrentMapCache.setName("defaultCache");
        return defaultConcurrentMapCache;
    }

    @Bean(name = "defaultCache")
    public ConcurrentMapCache defaultCache() {
        return defaultCacheFactoryBean.getObject();
    }

    @Bean(name = "simpleCacheFactoryBean", initMethod = "afterPropertiesSet")
    public ConcurrentMapCacheFactoryBean simpleCacheFactoryBean() {
        ConcurrentMapCacheFactoryBean defaultConcurrentMapCache = new ConcurrentMapCacheFactoryBean();
        defaultConcurrentMapCache.setName("simpleCache");
        return defaultConcurrentMapCache;
    }

    @Bean(name = "simpleCache")
    public ConcurrentMapCache getSimpleConcurrentMapCache() {
        return simpleCacheFactoryBean.getObject();
    }

    @Bean(name = "simpleCacheManager", initMethod = "afterPropertiesSet")
    public SimpleCacheManager getSimpleCacheManager() {
        SimpleCacheManager simpleCacheManager = new SimpleCacheManager();

        //set caches
        ArrayList<ConcurrentMapCache> caches = new ArrayList<>();
        caches.add(defaultCache);
        caches.add(simpleCache);
        simpleCacheManager.setCaches(caches);

        simpleCacheManager.afterPropertiesSet();
        return simpleCacheManager;
    }

}
