package com.zjj.gateway;


import com.zjj.security.tenant.component.EnableReactiveTenantJwtCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
//import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
//import org.springframework.security.web.server.WebFilterChainProxy;


/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年09月27日 20:20
 */
@Slf4j
@EnableReactiveTenantJwtCache
@EnableWebFluxSecurity
@SpringBootApplication
public class ZeroDDDGatewayApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(ZeroDDDGatewayApplication.class, args);
		log.info(LOGO);
	}

	private static final String LOGO = """
			                                           ██      ██      ██                           ██                                       \s
			                                          ░██     ░██     ░██        █████             ░██                                 ██   ██
			 ██████  █████  ██████  ██████            ░██     ░██     ░██       ██░░░██  ██████   ██████  █████  ███     ██  ██████   ░░██ ██\s
			░░░░██  ██░░░██░░██░░█ ██░░░░██ █████  ██████  ██████  ██████ █████░██  ░██ ░░░░░░██ ░░░██░  ██░░░██░░██  █ ░██ ░░░░░░██   ░░███ \s
			   ██  ░███████ ░██ ░ ░██   ░██░░░░░  ██░░░██ ██░░░██ ██░░░██░░░░░ ░░██████  ███████   ░██  ░███████ ░██ ███░██  ███████    ░██  \s
			  ██   ░██░░░░  ░██   ░██   ░██      ░██  ░██░██  ░██░██  ░██       ░░░░░██ ██░░░░██   ░██  ░██░░░░  ░████░████ ██░░░░██    ██   \s
			 ██████░░██████░███   ░░██████       ░░██████░░██████░░██████        █████ ░░████████  ░░██ ░░██████ ███░ ░░░██░░████████  ██    \s
			░░░░░░  ░░░░░░ ░░░     ░░░░░░         ░░░░░░  ░░░░░░  ░░░░░░        ░░░░░   ░░░░░░░░    ░░   ░░░░░░ ░░░    ░░░  ░░░░░░░░  ░░     \s
			""";
}
