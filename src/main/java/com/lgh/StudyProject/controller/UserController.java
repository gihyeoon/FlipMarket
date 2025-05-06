package com.lgh.StudyProject.controller;

import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lgh.StudyProject.entity.User;
import com.lgh.StudyProject.repository.UserRepository;

@Controller // Template을 사용할 경우에는 RestController는 사용하면 안된다.
public class UserController {

	private final UserRepository userRepository;

	private final BCryptPasswordEncoder passwordEncoder;

	public UserController(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@GetMapping("/register")
	public String registerForm() {
		return "register";
	}

	@GetMapping("/login")
	public String loginForm() {
		return "login";
	}

	@GetMapping("/findPassword")
	public String findPasswordForm() {
		return "findPassword";
	}

	@GetMapping("/mypage")
	public String myPageForm(@RequestParam("num") Long num, Model model) {
		User user = userRepository.findById(num).orElseThrow(() -> new RuntimeException("User not found"));

		model.addAttribute("num", num);
		model.addAttribute("id", user.getId());
		model.addAttribute("name", user.getName());
		model.addAttribute("age", user.getAge());
		model.addAttribute("phoneNum", user.getPhoneNum());

		return "mypage";
	}

	@GetMapping("/mypage/reConfirmUserInfo")
	public String reConfirmUserInfoForm(@RequestParam("num") Long num, Model model) {
		User user = userRepository.findById(num).orElseThrow(() -> new RuntimeException("User not found"));

		model.addAttribute("num", num);
		model.addAttribute("id", user.getId());
		model.addAttribute("pwd", user.getPwd());

		return "reConfirmUserInfo";
	}

	@ResponseBody
	@PostMapping("/api/validateUser")
	public int validateUser(@RequestBody Map<String, String> data) {
		User user = userRepository.findById(data.get("id")).orElseThrow(() -> new RuntimeException("User not found"));
		String loginUserPwd = user.getPwd();

		if (passwordEncoder.matches(data.get("enteredPwd"), loginUserPwd)) {
			return 1;
		} else {
			return 0;
		}
	}

	@GetMapping("/mypage/editProfile")
	public String editProfileForm(@RequestParam("num") Long num, Model model) {
		User user = userRepository.findById(num).orElseThrow(() -> new RuntimeException("User not found"));

		model.addAttribute("num", num);
		model.addAttribute("id", user.getId());
		model.addAttribute("name", user.getName());
		model.addAttribute("pwd", user.getPwd());
		model.addAttribute("phoneNum", user.getPhoneNum());
		model.addAttribute("age", user.getAge());

		return "editProfile";
	}

	@GetMapping("/resetPassword")
	public String resetPasswordForm(@RequestParam("id") String id, Model model) {
		User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
		
		model.addAttribute("num", user.getNum());
		
		return "resetPassword";
	}

}
