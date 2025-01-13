package com.zjj.domain.component.event;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.modulith.events.IncompleteEventPublications;
import org.springframework.modulith.events.config.EventPublicationAutoConfiguration;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年01月13日 21:07
 */
@AutoConfiguration(after = EventPublicationAutoConfiguration.class)
@EnableConfigurationProperties(EventTryAgainProperties.class)
public class EventSourceAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(IncompleteEventTryAgain.class)
    @ConditionalOnBean({IncompleteEventPublications.class})
    public IncompleteEventTryAgain incompleteEventTryAgain(IncompleteEventPublications incompleteEventPublications, EventTryAgainProperties eventTryAgainProperties) {
        return new DefaultIncompleteEventTryAgain(incompleteEventPublications, eventTryAgainProperties.getBeforeDuration(), eventTryAgainProperties.getInterval());
    }
}
