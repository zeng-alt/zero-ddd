package com.zjj.domain.component.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月06日 16:13
 */
@EnableJpaAuditing
@AutoConfiguration
public class DomainAutoConfiguration {

//    @Resource
//    private RedisReliableTopicRepositoryImpl repository;


//    @Bean
//    public CurrentTenantProvider currentTenantProvider() {
//        return new CurrentTenantProvider();
//    }

    @Bean
    public CurrentAuditorProvider currentAuditorProvider() {
        return new CurrentAuditorProvider();
    }

//    @Bean
//    public DomainEventAccessor redisDomainEventProvider() {
//        return new DomainEventAccessor() {
//            @Override
//            public <T> String addListener(String topic, Class<T> domainEventClass, DomainEventConsumer<T> listener) {
//                return repository.addListener(topic, domainEventClass, listener);
//            }
//
//            @Override
//            public <T> long publish(String topic, T domainEvent) {
//                return repository.publish(topic, domainEvent);
//            }
//        };
//    }
}