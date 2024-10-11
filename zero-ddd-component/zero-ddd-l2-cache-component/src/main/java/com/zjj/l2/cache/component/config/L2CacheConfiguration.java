package com.zjj.l2.cache.component.config;

import com.zjj.cache.component.repository.RedisStringRepository;
import com.zjj.l2.cache.component.config.properties.L2CacheProperties;
import com.zjj.l2.cache.component.supper.L2CacheManage;
import com.zjj.l2.cache.component.supper.RedissonCaffeineCacheManage;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月08日 21:05
 */
@AutoConfiguration(after = RedisAutoConfiguration.class)
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(L2CacheProperties.class)
public class L2CacheConfiguration {

    @Bean
    @ConditionalOnMissingBean(L2CacheManage.class)
    public L2CacheManage l2CacheManage(L2CacheProperties l2CacheProperties, RedisStringRepository redisStringRepository) {
        return new RedissonCaffeineCacheManage(l2CacheProperties, redisStringRepository);
    }
}
