package com.lgh.StudyProject.controller.user;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lgh.StudyProject.dto.ProductDto;
import com.lgh.StudyProject.dto.UserDto;
import com.lgh.StudyProject.service.ProductService;
import com.lgh.StudyProject.service.UserService;

@Controller
public class UserController {

	private final UserService userService;
	
	private final ProductService productService;

	private static final String BASE_URL = "user/";

	public UserController(UserService userService, ProductService productService) {
		this.userService = userService;
		this.productService = productService;
	}

	@GetMapping("/register")
	public String showRegisterPage() {
		return "register";
	}

	@GetMapping("/login")
	public String showLoginPage() {
		return "login";
	}

	@GetMapping("/findPassword")
	public String showFindPasswordPage() {
		return BASE_URL + "findPassword";
	}

	@GetMapping("/mypage")
	public String showMypage(@RequestParam("num") Long userNum, Model model) {
		UserDto userDto = userService.findByNum(userNum);
		
		// 사용자가 가장 최근에 등록한 3개의 상품들만 조회
		List<ProductDto> recentProducts = productService.findByUserNum(userNum); 
		
		model.addAttribute("user", userDto);
		model.addAttribute("recentProducts", recentProducts);
		return BASE_URL + "mypage";
	}

	@GetMapping("/mypage/reConfirmUserInfo")
	public String showReConfirmUserInfoPage(@RequestParam("num") Long userNum, Model model) {
		UserDto userDto = userService.findByNum(userNum);

		model.addAttribute("num", userDto.getNum());
		model.addAttribute("email", userDto.getEmail());
		model.addAttribute("pwd", userDto.getPwd());

		return BASE_URL + "reConfirmUserInfo";
	}

	@GetMapping("/mypage/editProfile")
	public String showEditProfilePage(@RequestParam("num") Long userNum, Model model) {
		UserDto userDto = userService.findByNum(userNum);
		model.addAttribute("user", userDto);
		return BASE_URL + "editProfile";
	}

	@GetMapping("/resetPassword")
	public String showResetPasswordPage(@RequestParam("email") String userEmail, Model model) {
		Long userNum = userService.findNumByEmail(userEmail);
		UserDto userDto = userService.findByNum(userNum);

		model.addAttribute("num", userDto.getNum());

		return BASE_URL + "resetPassword";
	}

}
