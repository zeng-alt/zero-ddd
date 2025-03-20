package com.zjj.auth;

import com.zjj.autoconfigure.component.tenant.TenantMode;
//import com.zjj.security.tenant.component.EnableTenantJwtCache;
//import com.zjj.tenant.datasource.component.configuration.EnableMultiTenancy;
import com.zjj.security.tenant.component.EnableTenantJwtCache;
import com.zjj.tenant.datasource.component.configuration.EnableMultiTenancy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年09月27日 21:20
 */
@Slf4j
@EnableMultiTenancy(mode = TenantMode.MIXED)
@EnableTenantJwtCache
@EnableFeignClients(basePackages = "com.zjj")
@EnableWebSecurity(debug = true)
@SpringBootApplication
public class ZeroDddAuthApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(ZeroDddAuthApplication.class, args);
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
