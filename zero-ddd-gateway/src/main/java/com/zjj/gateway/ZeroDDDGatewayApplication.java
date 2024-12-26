package com.zjj.gateway;


import com.zjj.security.tenant.component.EnableReactiveTenantJwtCache;
import com.zjj.security.tenant.component.EnableTenantJwtCache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
//import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
//import org.springframework.security.web.server.WebFilterChainProxy;


/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年09月27日 20:20
 */
@EnableReactiveTenantJwtCache
@EnableWebFluxSecurity
@SpringBootApplication
public class ZeroDDDGatewayApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(ZeroDDDGatewayApplication.class, args);
		System.out.println(run);
	}
}
