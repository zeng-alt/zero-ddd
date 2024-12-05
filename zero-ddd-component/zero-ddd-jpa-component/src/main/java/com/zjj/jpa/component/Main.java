package com.zjj.jpa.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年07月03日 20:48
 */
@SpringBootApplication
public class Main implements CommandLineRunner {

	@Autowired
	private List<JpaBaseEntity> jpaBaseEntitys;

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);

		TestEntity testEntity = new TestEntity();
		testEntity.save();
		testEntity = new TestEntity();
		testEntity.save();
		testEntity = new TestEntity();
		testEntity.save();
		testEntity = new TestEntity();
		testEntity.save();
		Page<TestEntity> testEntities = testEntity.pageByList(1, 10);
		System.out.println(testEntities);
	}

	@Override
	public void run(String... args) throws Exception {
		for (JpaBaseEntity jpaBaseEntity : jpaBaseEntitys) {
			System.out.println(jpaBaseEntity);
		}
	}

}
