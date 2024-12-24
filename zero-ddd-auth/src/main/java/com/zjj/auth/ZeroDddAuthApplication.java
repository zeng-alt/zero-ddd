package com.zjj.auth;


import com.zjj.l2.cache.component.config.EnableL2Cache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年09月27日 09:20
 */
@EnableWebSecurity(debug = true)
@EnableL2Cache
@EnableCaching
@EnableJpaRepositories(basePackages = "com.zjj")
@SpringBootApplication
public class ZeroDddAuthApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(ZeroDddAuthApplication.class, args);

		System.out.println(run);
	}
}
