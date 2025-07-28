package com.zjj.camunda;

import com.zjj.autoconfigure.component.tenant.TenantMode;
import com.zjj.security.tenant.component.EnableTenantJwtCache;
import com.zjj.tenant.datasource.component.configuration.EnableMultiTenancy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年07月28日 15:58
 */
@Slf4j
//@EnableMultiTenancy(mode = TenantMode.MIXED)
//@EnableTenantJwtCache
//@EnableFeignClients(basePackages = "com.zjj")
@EnableWebSecurity(debug = true)
@SpringBootApplication
public class ZeroDddCamundaApplication {

    public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(ZeroDddCamundaApplication.class, args);
        log.info(LOGO);
    }

    private static String LOGO = """
			                                           ██      ██      ██                           ██   ██    \s
			                                          ░██     ░██     ░██                          ░██  ░██    \s
			 ██████  █████  ██████  ██████            ░██     ░██     ░██        ██████   ██   ██ ██████░██    \s
			░░░░██  ██░░░██░░██░░█ ██░░░░██ █████  ██████  ██████  ██████ █████ ░░░░░░██ ░██  ░██░░░██░ ░██████\s
			   ██  ░███████ ░██ ░ ░██   ░██░░░░░  ██░░░██ ██░░░██ ██░░░██░░░░░   ███████ ░██  ░██  ░██  ░██░░░██
			  ██   ░██░░░░  ░██   ░██   ░██      ░██  ░██░██  ░██░██  ░██       ██░░░░██ ░██  ░██  ░██  ░██  ░██
			 ██████░░██████░███   ░░██████       ░░██████░░██████░░██████      ░░████████░░██████  ░░██ ░██  ░██
			░░░░░░  ░░░░░░ ░░░     ░░░░░░         ░░░░░░  ░░░░░░  ░░░░░░        ░░░░░░░░  ░░░░░░    ░░  ░░   ░░\s
			   
			""";
}
