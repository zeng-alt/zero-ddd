package com.zjj.l2.cache.component.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月11日 20:35
 */
@Data
@Component
@ConfigurationProperties(prefix = "spring.cache.multi.cache")
public class CacheProperties {

    /**
     * 全局过期时间，默认不过期
     */
    private Duration defaultExpiration = Duration.ZERO;

    /**
     * 全局空值过期时间，默认和有值的过期时间一致，一般设置空值过期时间较短
     */
    private Duration defaultNullValuesExpiration = null;

    /**
     * 每个cacheName的过期时间，优先级比defaultExpiration高
     */
    private Map<String, Duration> expires = new ConcurrentHashMap<>();

    /**
     * 每个cacheName的设置空值过期时间，优先级比defaultNullValuesExpiration高
     */
    private Map<String, Duration> nullValueExpires = new ConcurrentHashMap<>();

    /**
     * 缓存更新时通知其他节点的topic名称
     */
    private String topic = "cache:redis:caffeine:topic";

    /**
     * 生成当前节点id的key，当配置了spring.cache.multi.server-id时，该配置不生效
     */
    private String serverIdGeneratorKey = "cache:redis:caffeine:server-id-sequence";
}
