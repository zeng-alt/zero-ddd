package com.zjj.l2.cache.component.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.zjj.autoconfigure.component.l2cache.provider.EventSubPubProvider;
import com.zjj.autoconfigure.component.l2cache.SequenceServerIdGenerator;
import com.zjj.autoconfigure.component.l2cache.provider.L2CacheManageProvider;
import com.zjj.cache.component.provider.RedisEventSubPubProvider;
import com.zjj.autoconfigure.component.redis.RedisStringRepository;
import com.zjj.cache.component.repository.impl.RedisReliableTopicRepositoryImpl;
import com.zjj.l2.cache.component.config.properties.L2CacheProperties;
import com.zjj.autoconfigure.component.l2cache.EvictEvent;
import com.zjj.l2.cache.component.listener.CacheEvictEventListener;
import com.zjj.autoconfigure.component.l2cache.L2CacheManage;
import com.zjj.l2.cache.component.provider.L2BuilderProvider;
import com.zjj.l2.cache.component.provider.L2CacheProvider;
import com.zjj.l2.cache.component.supper.RedissonCaffeineCache;
import com.zjj.l2.cache.component.supper.RedissonCaffeineCacheManage;
import com.zjj.l2.cache.component.supper.RedissonSequenceServerIdGenerator;
import com.zjj.memory.component.provider.Tuple;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月08日 21:05
 */
@Slf4j
@AutoConfiguration(after = RedisAutoConfiguration.class)
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(L2CacheProperties.class)
public class L2CacheConfiguration {

    @Bean
    @ConditionalOnMissingBean(L2CacheManage.class)
    public L2CacheManage l2CacheManage(
            L2CacheProperties l2CacheProperties,
            RedisStringRepository redisStringRepository,
            EventSubPubProvider<EvictEvent> eventSubPubProvider,
            SequenceServerIdGenerator sequenceServerIdGenerator,
            ObjectProvider<L2CacheProvider> l2CacheProviders,
            ObjectProvider<L2BuilderProvider> l2BuilderProviders,
            ObjectProvider<L2CacheManageProvider> l2CacheManageProviders
    ) {
        if (Objects.isNull(l2CacheProperties.getServerId())) {
            l2CacheProperties.setServerId(sequenceServerIdGenerator.nextId());
        }
        RedissonCaffeineCacheManage redissonCaffeineCacheManage = new RedissonCaffeineCacheManage(l2CacheProperties, redisStringRepository, eventSubPubProvider);
        eventSubPubProvider.addListener(l2CacheProperties.getL2Cache().getTopic(), EvictEvent.class, new CacheEvictEventListener(redissonCaffeineCacheManage));
        l2CacheProviders.orderedStream().forEach(l2 -> {
            Tuple<Cache<Object, Object>> tuple = l2.consumerCache();
            redissonCaffeineCacheManage.registerCustomCache(tuple._1(), tuple._2());
        });
        l2BuilderProviders.orderedStream().forEach(l2 -> {
            Tuple<RedissonCaffeineCache.Builder> tuple = l2.consumerBuilder();
            redissonCaffeineCacheManage.registerCustomCache(tuple._1(), tuple._2());
        });
        l2CacheManageProviders.orderedStream().forEach(l2 -> l2.consumer(redissonCaffeineCacheManage));
        redissonCaffeineCacheManage.setCacheNames(l2CacheProperties.getCacheNames());
        log.info("初始化l2Cache => redis和caffeine实现");
        return redissonCaffeineCacheManage;
    }

    @Bean
    @ConditionalOnMissingBean
    public EventSubPubProvider<EvictEvent> eventSubPubProvider(RedisReliableTopicRepositoryImpl redisReliableTopicRepository) {
        return new RedisEventSubPubProvider<>(redisReliableTopicRepository);
    }

    @Bean
    @ConditionalOnMissingBean
    public SequenceServerIdGenerator sequenceServerIdGenerator(L2CacheProperties l2CacheProperties, RedisStringRepository redisStringRepository) {
        return new RedissonSequenceServerIdGenerator(l2CacheProperties.getL2Cache().getServerIdGeneratorKey(), redisStringRepository);
    }
}
