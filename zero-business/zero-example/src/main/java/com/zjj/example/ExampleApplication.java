package com.zjj.example;

import com.zjj.autoconfigure.component.tenant.TenantMode;
import com.zjj.tenant.datasource.component.configuration.EnableMultiTenancy;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年02月19日 15:03
 */
@EnableCaching
@EnableRabbit
@EnableMultiTenancy(mode = TenantMode.DATABASE)
@SpringBootApplication
public class ExampleApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(ExampleApplication.class, args);
//        run.getBean(TenantRepository.class).findAll();
        System.out.println();
    }


}
