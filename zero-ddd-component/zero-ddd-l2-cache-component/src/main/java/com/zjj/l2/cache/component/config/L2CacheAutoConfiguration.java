package com.zjj.l2.cache.component.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月08日 21:05
 */
@AutoConfiguration(after = RedisAutoConfiguration.class)
public class L2CacheAutoConfiguration {

}
