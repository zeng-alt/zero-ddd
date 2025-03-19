package com.zjj.auth;

import com.zjj.autoconfigure.component.tenant.TenantMode;
import com.zjj.exchange.tenant.client.RemoteTenantClient;
import com.zjj.security.tenant.component.EnableTenantJwtCache;
import com.zjj.tenant.datasource.component.EnableMultiTenancy;
import com.zjj.tenant.management.component.annotations.EnableMasterJpaRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年09月27日 21:20
 */
@EnableMultiTenancy(mode = TenantMode.COLUMN)
@EnableTenantJwtCache
@EnableFeignClients(basePackages = "com.zjj")
@EnableWebSecurity(debug = true)
@SpringBootApplication
public class ZeroDddAuthApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(ZeroDddAuthApplication.class, args);
		System.out.println(run);
	}
}
