package com.zjj.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zjj.auth.service.UserService;
import com.zjj.autoconfigure.component.json.JsonHelper;
import com.zjj.bean.componenet.ApplicationContextHelper;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.test.context.SpringBootTest;

import java.beans.PropertyDescriptor;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class MainTest {

	@Autowired
	private JsonHelper jsonHelper;

	@Test
	public void runTest() {

	}

	@Test
	public void applicationContextHelperTest() {
		UserService bean = ApplicationContextHelper.getBean(UserService.class);
		System.out.println(bean.hello());
	}

	@Test
	public void jackJsonTest() {
		JsonHelper bean = ApplicationContextHelper.getBean(JsonHelper.class);
		SecurityProperties.User user = new SecurityProperties.User();
		user.setName("name");
		user.setPassword("123456");
		System.out.println(bean.toJsonString(user));
	}


	@Autowired
	private ObjectMapper objectMapper;


	@Test
	public void jackJsonDateTest() throws JsonProcessingException {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		System.out.println(LocalDateTime.parse("2024-10-10 14:56:52", formatter));
		System.out.println(now.format(formatter));
		System.out.println(jsonHelper.toJsonString(now));
		System.out.println(objectMapper.convertValue("2024-10-10 14:40:21", LocalDateTime.class));
		System.out.println(jsonHelper.parseObject("2024-10-10 14:40:21", LocalDateTime.class));
	}

	@Test
	public void jsonHelperTest() throws JsonProcessingException {
		SecurityProperties.User user = new SecurityProperties.User();
		user.setName("name");
		user.setPassword("123456");
		System.out.println(jsonHelper.toJsonString(user));

		LocalDateTime now = LocalDateTime.now();
		Map<String, Object> map = new HashMap<>();
		map.put("time", now);
		System.out.println(jsonHelper.toJsonString(map));

		System.out.println(jsonHelper.toJsonString(LocalDateTime.now()));

		String jsonString = objectMapper.writeValueAsString(LocalDateTime.now());
		System.out.println(jsonString);
	}



}
