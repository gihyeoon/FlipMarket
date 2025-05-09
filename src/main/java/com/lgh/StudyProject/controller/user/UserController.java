package com.lgh.StudyProject.controller.user;

import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lgh.StudyProject.dto.UserDto;
import com.lgh.StudyProject.service.UserService;

@Controller // Template을 사용할 경우에는 RestController는 사용하면 안된다.
public class UserController {

	private final UserService userService;

	private final BCryptPasswordEncoder passwordEncoder;

	public UserController(UserService userService, BCryptPasswordEncoder passwordEncoder) {
		this.userService = userService;
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
		UserDto userDto = userService.findByNum(num);
		model.addAttribute("user", userDto);
		return "mypage";
	}

	@GetMapping("/mypage/reConfirmUserInfo")
	public String reConfirmUserInfoForm(@RequestParam("num") Long num, Model model) {
		UserDto userDto = userService.findByNum(num);

		model.addAttribute("num", userDto.getNum());
		model.addAttribute("email", userDto.getEmail());
		model.addAttribute("pwd", userDto.getPwd());

		return "reConfirmUserInfo";
	}

	@ResponseBody
	@PostMapping("/api/validateUser")
	public int validateUser(@RequestBody Map<String, String> data) {
		Long userNum = userService.findNumByEmail(data.get("email"));
		UserDto userDto = userService.findByNum(userNum);
		String loginUserPwd = userDto.getPwd();

		if (passwordEncoder.matches(data.get("enteredPwd"), loginUserPwd)) {
			return 1;
		} else {
			return 0;
		}
	}

	@GetMapping("/mypage/editProfile")
	public String editProfileForm(@RequestParam("num") Long num, Model model) {
		UserDto userDto = userService.findByNum(num);
		model.addAttribute("user", userDto);
		return "editProfile";
	}

	@GetMapping("/resetPassword")
	public String resetPasswordForm(@RequestParam("email") String email, Model model) {
		Long userNum = userService.findNumByEmail(email);
		UserDto userDto = userService.findByNum(userNum);

		model.addAttribute("num", userDto.getNum());

		return "resetPassword";
	}

}
