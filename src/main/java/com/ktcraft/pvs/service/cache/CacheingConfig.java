package com.ktcraft.pvs.service.cache;

import com.ktcraft.pvs.constants.AppConstants;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheingConfig {
    public static CacheManager cacheManager;

    @Bean
    public CacheManager cacheManager() {
        final CacheManager cacheManager = new ConcurrentMapCacheManager(
                AppConstants.LOCAL_CACHE_KEY, AppConstants.PROFILE_CACHE_KEY, AppConstants.PROFILE_UPDATE_CACHE_KEY);
        CacheingConfig.cacheManager = cacheManager;
        return cacheManager;
    }
}
