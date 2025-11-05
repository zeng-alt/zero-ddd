package com.zjj.event.temporal.component;

import io.temporal.client.WorkflowClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.modulith.events.EventExternalizationConfiguration;
import org.springframework.modulith.events.config.EventExternalizationAutoConfiguration;
import org.springframework.modulith.events.support.BrokerRouting;
import org.springframework.modulith.events.support.DelegatingEventExternalizer;

import java.util.concurrent.CompletableFuture;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年09月18日 16:08
 */
@AutoConfiguration
@AutoConfigureAfter(EventExternalizationAutoConfiguration.class)
@ConditionalOnClass(WorkflowClient.class)
@ConditionalOnProperty(name = "spring.modulith.events.externalization.enabled",
        havingValue = "true",
        matchIfMissing = true)
public class TemporalEventExternalizerConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(TemporalEventExternalizerConfiguration.class);

    @Bean
    DelegatingEventExternalizer temporalEventExternalizer(EventExternalizationConfiguration configuration,
                                                          WorkflowClient workflowClient,
                                                           BeanFactory factory) {

        logger.debug("Registering domain event externalization to Temporal…");

        var context = new StandardEvaluationContext();
        context.setBeanResolver(new BeanFactoryResolver(factory));

        return new DelegatingEventExternalizer(configuration, (target, payload) -> {

//            workflowClient.newUntypedWorkflowStub(workflowId);

            var routing = BrokerRouting.of(target, context);
            var headers = configuration.getHeadersFor(payload);

//            operations.convertAndSend(routing.getTarget(payload), routing.getKey(payload), payload, headers);

            return CompletableFuture.completedFuture(null);
        });
    }
}
