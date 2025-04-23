package com.lgh.StudyProject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Controller // Template을 사용할 경우에는 RestController는 사용하면 안된다.
@Slf4j
public class UserController {

	@GetMapping("/register")
	public String registerForm() {
		return "register";
	}

	@GetMapping("/login")
	public String loginForm() {
		return "login";
	}
	
}
