package com.starfish.common.cache;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

/**
 * 缓存配置,当前spring5还不支持ehcache3
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2015-08-04
 * @deprecated since 2022-08-01
 */
//@Configuration
//@EnableCaching(proxyTargetClass = true)
@Deprecated
public class EhCacheConfiguration {

//    @Autowired
//    @Qualifier("ehCacheManager")
//    private CacheManager ehCache;
//
//    @Bean(name = "ehCacheManager")
//    public CacheManager getEhCacheManagerFactory() {
//        EhCacheManagerFactoryBean ehCacheManagerFactory = new EhCacheManagerFactoryBean();
//
//        //load resource
//        ResourceLoader resourceLoader = new DefaultResourceLoader();
//        Resource resource = resourceLoader.getResource("classpath*:ehcache.xml");
//        ehCacheManagerFactory.setConfigLocation(resource);
//
//        return ehCacheManagerFactory.getObject();
//    }
//
//    @Bean(name = "cacheManager")
//    public EhCacheCacheManager getEhCacheCacheManager() {
//        EhCacheCacheManager ehCacheManager = new EhCacheCacheManager();
//        ehCacheManager.setCacheManager(ehCache);
//        return ehCacheManager;
//    }

}
