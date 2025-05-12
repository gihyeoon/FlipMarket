package com.lgh.StudyProject.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lgh.StudyProject.dto.UserDto;
import com.lgh.StudyProject.service.UserService;

@Controller
public class UserController {

	private final UserService userService;

	private final String baseUrl = "user/";

	public UserController(UserService userService) {
		this.userService = userService;
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
		return baseUrl + "findPassword";
	}

	@GetMapping("/mypage")
	public String myPageForm(@RequestParam("num") Long userNum, Model model) {
		UserDto userDto = userService.findByNum(userNum);
		model.addAttribute("user", userDto);
		return baseUrl + "mypage";
	}

	@GetMapping("/mypage/reConfirmUserInfo")
	public String reConfirmUserInfoForm(@RequestParam("num") Long userNum, Model model) {
		UserDto userDto = userService.findByNum(userNum);

		model.addAttribute("num", userDto.getNum());
		model.addAttribute("email", userDto.getEmail());
		model.addAttribute("pwd", userDto.getPwd());

		return baseUrl + "reConfirmUserInfo";
	}

	@GetMapping("/mypage/editProfile")
	public String editProfileForm(@RequestParam("num") Long userNum, Model model) {
		UserDto userDto = userService.findByNum(userNum);
		model.addAttribute("user", userDto);
		return baseUrl + "editProfile";
	}

	@GetMapping("/resetPassword")
	public String resetPasswordForm(@RequestParam("email") String userEmail, Model model) {
		Long userNum = userService.findNumByEmail(userEmail);
		UserDto userDto = userService.findByNum(userNum);

		model.addAttribute("num", userDto.getNum());

		return baseUrl + "resetPassword";
	}

}
