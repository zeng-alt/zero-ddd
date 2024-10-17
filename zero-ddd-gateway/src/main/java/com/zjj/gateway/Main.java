package com.zjj.gateway;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年09月27日 09:20
 */
@EnableJpaRepositories
@SpringBootApplication
public class Main {

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(Main.class, args);
		System.out.println(run);
	}
}
