package com.zjj.example.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年02月21日 17:08
 */
@Configuration
public class RabbitConfig {

    @Bean
    public Queue queue() {
        return new Queue("ddd-event-test");
    }


    @Bean
    DirectExchange eventExchange() {
        return new DirectExchange("ddd-event-test");
    }

    @Bean
    Binding bindingExchangeA(Queue queue, DirectExchange eventExchange) {
        return BindingBuilder.bind(queue).to(eventExchange).withQueueName();
    }

}
