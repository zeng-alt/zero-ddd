package com.zjj.tenant.infrastructure.config;

import com.zjj.tenant.domain.tenant.event.CreateTenantDataSourceEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.modulith.events.EventExternalizationConfiguration;
import org.springframework.modulith.events.RoutingTarget;

import java.util.Objects;

@Configuration
class ExternalizationConfiguration {


    @Bean
    EventExternalizationConfiguration eventExternalizationConfiguration() {
        return EventExternalizationConfiguration.externalizing()
                .select(EventExternalizationConfiguration.annotatedAsExternalized())
                .route(
                        CreateTenantDataSourceEvent.class,
                        it -> RoutingTarget.forTarget("baeldung.articles.published").andKey(it.getTenantKey())
                )
                .mapping(
                        CreateTenantDataSourceEvent.class,
                        it -> new PostPublishedKafkaEvent(it.getTenantKey(), null)
                )
                .build();
    }

    record PostPublishedKafkaEvent(String slug, String title) {
        PostPublishedKafkaEvent {
            Objects.requireNonNull(slug, "Article Slug must not be null!");
        }
    }
}