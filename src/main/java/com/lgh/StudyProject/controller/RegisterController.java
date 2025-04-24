package com.lgh.StudyProject.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lgh.StudyProject.dto.UserDto;
import com.lgh.StudyProject.service.UserService;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class RegisterController {
	
	private final UserService userService;
	
	private final BCryptPasswordEncoder passwordEncoder;
	
	public RegisterController(UserService userService, BCryptPasswordEncoder passwordEncoder) {
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
	}

	@GetMapping("/overlap/idRegister")
	public Map<String, String> idRegister(@RequestParam(value = "id") String id) {
		return Map.of("result", userService.checkDuplicateId(id) ? "1" : "0");
	}
	
	@PostMapping("/register")
	public Long register(@RequestBody UserDto userDto) {
		String encodedPwd = passwordEncoder.encode(userDto.getPwd());
		userDto.setPwd(encodedPwd);
		return userService.register(userDto);
	}
	
}
