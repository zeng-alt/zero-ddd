package com.zjj.auth;

//import com.zjj.l2.cache.component.config.EnableL2Cache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;


/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年09月27日 09:20
 */
// @EnableWebSecurity
//@EnableL2Cache
@EnableCaching
@SpringBootApplication
public class Main {

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(Main.class, args);
		System.out.println(run);
	}
}
