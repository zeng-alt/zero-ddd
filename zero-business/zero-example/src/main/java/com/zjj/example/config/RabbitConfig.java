package com.zjj.example.config;

import com.zjj.example.ExampleApplication;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.modulith.events.ApplicationModuleListener;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年02月21日 17:08
 */
@Configuration
public class RabbitConfig {

    @Value("${spring.application.name}")
    private String name;

    @Bean
    public Queue queue() {
        return new Queue(name);
    }


    @Bean
    DirectExchange eventExchange() {
        return new DirectExchange("ddd-event-test");
    }

    @Bean
    Binding bindingExchangeA(Queue queue, DirectExchange eventExchange) {
        return BindingBuilder.bind(queue).to(eventExchange).withQueueName();
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }





}
