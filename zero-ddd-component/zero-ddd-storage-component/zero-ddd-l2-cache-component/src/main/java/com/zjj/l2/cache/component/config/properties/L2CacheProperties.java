package com.zjj.l2.cache.component.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月11日 20:13
 */
@Data
@Component
@ConfigurationProperties(prefix = "spring.cache.multi")
public class L2CacheProperties {

    private Set<String> cacheNames = new HashSet<>();

    /**
     * 是否存储空值，默认true，防止缓存穿透
     */
    private boolean cacheNullValues = true;

    /**
     * 是否动态根据cacheName创建Cache的实现，默认true
     */
    private boolean dynamic = true;

    /**
     * 缓存key的前缀
     */
    private String cachePrefix;

    /**
     * 当前节点id。来自当前节点的缓存更新通知不会被处理
     */
    private Object serverId;

    /** 一级缓存基于内存 */
    private Caffeine l1Cache = new Caffeine();


    /** 二级缓存基于外部cache, 使用redis */
    @NestedConfigurationProperty
    private CacheProperties l2Cache = new CacheProperties();


    @Data
    public static class Caffeine {

        /**
         * The spec to use to create caches. See CaffeineSpec for more details on the spec
         * format.
         */
        private String spec;

    }


}
