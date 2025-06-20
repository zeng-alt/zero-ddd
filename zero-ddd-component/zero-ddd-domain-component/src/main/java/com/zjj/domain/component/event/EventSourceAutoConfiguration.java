package com.zjj.domain.component.event;

import com.zjj.domain.component.event.endpoint.ModulithEventsActuatorEndpoint;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.modulith.events.CompletedEventPublications;
import org.springframework.modulith.events.EventExternalizationConfiguration;
import org.springframework.modulith.events.IncompleteEventPublications;
import org.springframework.modulith.events.config.EventExternalizationAutoConfiguration;
import org.springframework.modulith.events.config.EventPublicationAutoConfiguration;
import org.springframework.modulith.events.core.EventPublicationRepository;
import org.springframework.modulith.moments.HourHasPassed;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年01月13日 21:07
 */
@ConditionalOnProperty(name = "spring.modulith.moments.enabled", havingValue = "true", matchIfMissing = true)
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(EventPublicationAutoConfiguration.class)
@AutoConfiguration(after = EventPublicationAutoConfiguration.class)
public class EventSourceAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(IncompleteEventTryAgain.class)
    @ConditionalOnBean({IncompleteEventPublications.class})
    public IncompleteEventTryAgain incompleteEventTryAgain(IncompleteEventPublications incompleteEventPublications) {
        return new DefaultIncompleteEventTryAgain(incompleteEventPublications);
    }

    @Bean
    @ConditionalOnMissingBean(CompletedEventPublications.class)
    @ConditionalOnBean({CompletedEventTransfer.class})
    public CompletedEventTransfer completedEventTransfer(CompletedEventPublications completedEventPublications) {
        return new DefaultCompletedEventTransfer(completedEventPublications);
    }


    @Bean
    @ConditionalOnClass(name = "org.springframework.boot.actuator.endpoint.annotation.Endpoint")
    @ConditionalOnBean({IncompleteEventPublications.class, EventPublicationRepository.class})
    @ConditionalOnProperty(
            name = "modulith.events.actuator.enabled",
            havingValue = "true",
            matchIfMissing = true
    )
    public ModulithEventsActuatorEndpoint modulithEventsActuatorEndpoint(
            IncompleteEventPublications incompleteEventPublications,
            EventPublicationRepository eventPublicationRepository) {
        return new ModulithEventsActuatorEndpoint(incompleteEventPublications, eventPublicationRepository);
    }



//    @Bean
//    @ConditionalOnMissingBean(IncompleteEventTryAgain.class)
//    @ConditionalOnBean({IncompleteEventPublications.class})
//    public IncompleteEventTryAgain incompleteEventTryAgain(IncompleteEventPublications incompleteEventPublications, EventTryAgainProperties eventTryAgainProperties) {
//        return new DefaultIncompleteEventTryAgain(incompleteEventPublications, eventTryAgainProperties.getBeforeDuration(), eventTryAgainProperties.getInterval());
//    }



}
