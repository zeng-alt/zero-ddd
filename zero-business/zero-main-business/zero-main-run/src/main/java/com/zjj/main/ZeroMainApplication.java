package com.zjj.main;

import com.zjj.autoconfigure.component.tenant.TenantMode;
import com.zjj.graphql.component.annotations.EnableGenEntityInput;
import com.zjj.graphql.component.annotations.EnableGenEntityMutation;
import com.zjj.graphql.component.annotations.EnableGenEntityQuery;
import com.zjj.graphql.component.annotations.EnableGenEntityType;
import com.zjj.security.tenant.component.EnableTenantJwtCache;
import com.zjj.tenant.datasource.component.configuration.EnableMultiTenancy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月14日 21:08
 */
@Slf4j
@EnableTenantJwtCache
@EnableMultiTenancy(mode = TenantMode.MIXED)
@SpringBootApplication
@EnableFeignClients(basePackages = "com.zjj")
@EnableGenEntityType
@EnableGenEntityInput
@EnableGenEntityQuery
@EnableGenEntityMutation
public class ZeroMainApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(ZeroMainApplication.class, args);
        log.info(LOGO);
    }


    private static final String LOGO = """
                                                       ██      ██      ██            ██                                ██
                                                      ░██     ░██     ░██           ░██                               ░░
             ██████  █████  ██████  ██████            ░██     ░██     ░██           ░██  ██████  ██████████   ██████   ██ ███████
            ░░░░██  ██░░░██░░██░░█ ██░░░░██ █████  ██████  ██████  ██████ █████  ██████ ██░░░░██░░██░░██░░██ ░░░░░░██ ░██░░██░░░██
               ██  ░███████ ░██ ░ ░██   ░██░░░░░  ██░░░██ ██░░░██ ██░░░██░░░░░  ██░░░██░██   ░██ ░██ ░██ ░██  ███████ ░██ ░██  ░██
              ██   ░██░░░░  ░██   ░██   ░██      ░██  ░██░██  ░██░██  ░██      ░██  ░██░██   ░██ ░██ ░██ ░██ ██░░░░██ ░██ ░██  ░██
             ██████░░██████░███   ░░██████       ░░██████░░██████░░██████      ░░██████░░██████  ███ ░██ ░██░░████████░██ ███  ░██
            ░░░░░░  ░░░░░░ ░░░     ░░░░░░         ░░░░░░  ░░░░░░  ░░░░░░        ░░░░░░  ░░░░░░  ░░░  ░░  ░░  ░░░░░░░░ ░░ ░░░   ░░
                        
            """;
}
