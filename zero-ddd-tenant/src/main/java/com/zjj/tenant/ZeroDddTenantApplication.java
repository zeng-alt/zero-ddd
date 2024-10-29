package com.zjj.tenant;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月29日 21:34
 */
@EnableWebSecurity(debug = true)
@EnableJpaRepositories(basePackages = "com.zjj")
@SpringBootApplication
public class ZeroDddTenantApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(ZeroDddTenantApplication.class, args);

        System.out.println(run);
    }
}
