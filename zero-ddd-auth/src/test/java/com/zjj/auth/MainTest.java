//package com.zjj.auth;
//
//import com.google.common.collect.Maps;
//import com.zjj.auth.service.UserService;
//import com.zjj.autoconfigure.component.json.JsonHelper;
//import com.zjj.bean.componenet.ApplicationContextHelper;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.security.SecurityProperties;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.lang.Nullable;
//
//import java.time.LocalDateTime;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @author zengJiaJun
// * @version 1.0
// * @crateTime 2024年09月27日 020:22
// */
//@SpringBootTest
//public class MainTest {
//
//	@Autowired
//	private JsonHelper jsonHelper;
//
//	@Test
//	public void runTest() {
//
//	}
//
//	@Test
//	public void applicationContextHelperTest() {
//		UserService bean = ApplicationContextHelper.getBean(UserService.class);
//		System.out.println(bean.hello());
//	}
//
//	@Test
//	public void jackJsonTest() {
//		JsonHelper bean = ApplicationContextHelper.getBean(JsonHelper.class);
//		SecurityProperties.User user = new SecurityProperties.User();
//		user.setName("name");
//		user.setPassword("123456");
//		System.out.println(bean.toJsonString(user));
//	}
//
//	@Test
//	public void jsonHelperTest() {
//		SecurityProperties.User user = new SecurityProperties.User();
//		user.setName("name");
//		user.setPassword("123456");
//		System.out.println(jsonHelper.toJsonString(user));
//
//		LocalDateTime now = LocalDateTime.now();
//		Map<String, Object> map = new HashMap<>();
//		map.put("time", now);
//		System.out.println(jsonHelper.toJsonString(map));
//
//	}
//
//}
