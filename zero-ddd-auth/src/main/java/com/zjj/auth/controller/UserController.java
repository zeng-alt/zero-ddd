package com.zjj.auth.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author zengJiaJun
 * @crateTime 2024年07月11日 22:11
 * @version 1.0
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

	@GetMapping("/{name}")
	public List<String> getUsers(@PathVariable String name) {
		return List.of("Abc", "Def", "Ghi");
	}

	@GetMapping("/name")
	public List<String> getUser() {
		return List.of("name");
	}

	@GetMapping("/time")
	public LocalDateTime getTime() {
		return LocalDateTime.now();
	}


	@GetMapping("/time2")
	public User getTime2() {
		return new User();
	}

	@Data
	public static class User {
		private String name = "jok";
		private LocalDateTime now = LocalDateTime.now();
	}
}
