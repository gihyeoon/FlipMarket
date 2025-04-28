package com.lgh.StudyProject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lgh.StudyProject.entity.User;
import com.lgh.StudyProject.repository.UserRepository;

@Controller // Template을 사용할 경우에는 RestController는 사용하면 안된다.
public class UserController {
	
	private final UserRepository userRepository;
	
	public UserController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@GetMapping("/register")
	public String registerForm() {
		return "register";
	}

	@GetMapping("/login")
	public String loginForm() {
		return "login";
	}
	
	@GetMapping("/mypage")
	public String myPageForm(@RequestParam("num") Long num, Model model) {
		User user = userRepository.findById(num).orElseThrow(() -> new RuntimeException("User not found"));
		
		model.addAttribute("id", user.getId());
		model.addAttribute("name", user.getName());
		model.addAttribute("age", user.getAge());
		model.addAttribute("phoneNum", user.getPhoneNum());
		
		return "mypage";
	}
	
}
