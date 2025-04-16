package com.lgh.StudyProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lgh.StudyProject.entity.User;
import com.lgh.StudyProject.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Controller // Template을 사용할 경우에는 RestController는 사용하면 안된다.
@Slf4j
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("/register")
	public String registerForm() {
		return "register";
	}

	@GetMapping("/login")
	public String loginForm() {
		return "login";
	}

	@PostMapping("/register")
	public String register(@RequestParam(value = "id") String id, @RequestParam(value = "pwd") String pwd,
			@RequestParam(value = "name") String name, @RequestParam(value = "age") int age,
			@RequestParam(value = "phoneNum") String phoneNum) {
		String encodedPwd = passwordEncoder.encode(pwd);
		User user = new User(id, encodedPwd, name, age, phoneNum, "USER");
		userRepository.save(user);

		return "redirect:/login";
	}

}
