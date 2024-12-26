package com.zjj.auth;




import com.zjj.security.tenant.component.EnableTenantJwtCache;
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
@EnableTenantJwtCache
@EnableFeignClients(basePackages = "com.zjj")
@EnableWebSecurity(debug = true)
//@EnableL2Cache
//@EnableJpaRepositories(basePackages = "com.zjj")
@SpringBootApplication
public class ZeroDddAuthApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(ZeroDddAuthApplication.class, args);

		System.out.println(run);
	}
}
