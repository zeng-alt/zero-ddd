package com.zjj.auth;

import com.zjj.security.tenant.component.EnableTenantJwtCache;
import com.zjj.tenant.datasource.component.EnableMultiTenancy;
import com.zjj.tenant.datasource.component.TenantMode;
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
@EnableMultiTenancy(mode = TenantMode.SCHEMA)
@EnableTenantJwtCache
@EnableFeignClients(basePackages = "com.zjj")
@EnableWebSecurity(debug = true)
//@EnableL2Cache
@SpringBootApplication
public class ZeroDddAuthApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(ZeroDddAuthApplication.class, args);

		System.out.println(run);
	}
}
