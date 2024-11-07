package com.zjj.domain.component.config;

import com.zjj.cache.component.repository.impl.RedisReliableTopicRepositoryImpl;
import com.zjj.domain.component.DomainEvent;
import com.zjj.domain.component.DomainEventAccessor;
import com.zjj.domain.component.DomainEventConsumer;
import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.function.Consumer;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月06日 16:13
 */
@AutoConfiguration
public class DomainAutoConfiguration {

    @Resource
    private RedisReliableTopicRepositoryImpl repository;


    @Bean
    public DomainEventAccessor redisDomainEventProvider() {
        return new DomainEventAccessor() {
            @Override
            public <T> String addListener(String topic, Class<T> domainEventClass, DomainEventConsumer<T> listener) {
                return repository.addListener(topic, domainEventClass, listener);
            }

            @Override
            public <T> long publish(String topic, T domainEvent) {
                return repository.publish(topic, domainEvent);
            }
        };
    }
}
