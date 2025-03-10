package com.zjj.example;

import com.zjj.example.entity.UserEvent;
import lombok.Data;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.core.RabbitMessageOperations;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.modulith.events.Externalized;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年02月19日 15:03
 */
@EnableRabbit
@SpringBootApplication
public class ExampleApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(ExampleApplication.class, args);

    }


}
