package com.kenzie.appserver.config;
//for merge

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {

    // Create a Cache here if needed

//    @Bean
//    public CacheStore myCache() {
//        return new CacheStore(120, TimeUnit.SECONDS);
//    }
}
