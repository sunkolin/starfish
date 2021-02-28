package com.starfish.trial;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheFactoryBean;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;

/**
 * 缓存配置
 *
 * @author sunny
 * @version 1.0.0
 * @since 2015-05-22
 */
//@Configuration
//@EnableCaching(proxyTargetClass = true)
public class CacheConfiguration {

    @Autowired
    @Qualifier("defaultCacheFactoryBean")
    private ConcurrentMapCacheFactoryBean defaultCacheFactoryBean;

    @Autowired
    @Qualifier("simpleCacheFactoryBean")
    private ConcurrentMapCacheFactoryBean simpleCacheFactoryBean;

    @Autowired
    @Qualifier("defaultCache")
    private ConcurrentMapCache defaultCache;

    @Autowired
    @Qualifier("simpleCache")
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
        ArrayList<ConcurrentMapCache> caches = new ArrayList<ConcurrentMapCache>();
        caches.add(defaultCache);
        caches.add(simpleCache);
        simpleCacheManager.setCaches(caches);

        simpleCacheManager.afterPropertiesSet();
        return simpleCacheManager;
    }

}
