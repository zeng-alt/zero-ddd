package com.zjj.domain.component.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zjj.domain.component.command.*;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

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

    @Bean
    public JPAQueryFactory jpaQuery(@Qualifier("entityManagerFactory") EntityManager entityManager) {
        return new JPAQueryFactory(entityManager);
    }


    @Bean
    @ConditionalOnMissingBean
    public CommandDispatcher commandDispatcher() {
        return new SimpleCommandDispatcher();
    }

    @Bean
    @ConditionalOnMissingBean
    public CommandBus commandBus(CommandDispatcher commandDispatcher) {
        return new SimpleCommandBus(commandDispatcher);
    }

    @Bean
    @ConditionalOnMissingBean
    public CommandHandlerMethodProcessor commandHandlerMethodProcessor() {
        return new CommandHandlerMethodProcessor();
    }
}
