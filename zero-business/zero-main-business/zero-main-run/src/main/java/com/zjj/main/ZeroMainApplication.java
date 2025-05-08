package com.zjj.main;

import com.zjj.autoconfigure.component.tenant.TenantMode;
import com.zjj.graphql.component.annotations.*;
import com.zjj.security.abac.component.annotation.EnableAbac;
import com.zjj.security.tenant.component.EnableTenantJwtCache;
import com.zjj.tenant.datasource.component.configuration.EnableMultiTenancy;
import com.zjj.tenant.management.component.annotations.EnableMasterJpaRepositories;
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
@EnableAbac
@EnableTenantJwtCache
@EnableMultiTenancy(mode = TenantMode.MIXED)
@SpringBootApplication
@EnableFeignClients(basePackages = "com.zjj")
@EnableGenEntityType
@EnableGenEntityInput
@EnableGenEntityQuery
@EnableGenEntityFuzzyQuery
@EnableGenEntityMutation
@EnableGenEntityConditionQuery
@EnableMasterJpaRepositories(basePackages = "com.zjj.main")
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
