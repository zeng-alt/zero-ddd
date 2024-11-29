package com.zjj.gateway;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年09月27日 20:20
 */
@SpringBootApplication
public class ZeroDDDGatewayApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(ZeroDDDGatewayApplication.class, args);
		System.out.println(run);
	}
}
