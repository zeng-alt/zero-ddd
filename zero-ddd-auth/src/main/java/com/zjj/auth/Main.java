package com.zjj.auth;

import com.zjj.autoconfigure.component.security.jwt.JwtCacheManage;
import com.zjj.l2.cache.component.config.EnableL2Cache;
import com.zjj.security.tenant.component.configuration.EnableTenant;
import com.zjj.security.tenant.component.enums.CacheType;
import jakarta.persistence.EntityManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年09月27日 09:20
 */
@EnableWebSecurity(debug = true)
@EnableL2Cache
@EnableTenant(cacheType = CacheType.L2)
@EnableCaching
@EntityScan(basePackages = "com.zjj")
@EnableJpaRepositories(basePackages = "com.zjj")
@SpringBootApplication
public class Main {

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(Main.class, args);

		System.out.println(run);
	}
}
