package com.lgh.FlipMarket.controller.user;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.lgh.FlipMarket.dto.AddressDto;
import com.lgh.FlipMarket.dto.ProductDto;
import com.lgh.FlipMarket.dto.UserDto;
import com.lgh.FlipMarket.service.AddressService;
import com.lgh.FlipMarket.service.ProductService;
import com.lgh.FlipMarket.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class UserController {

	private final UserService userService;

	private final ProductService productService;

	private final AddressService addressService;

	private static final String BASE_URL = "user/";

	public UserController(UserService userService, ProductService productService, AddressService addressService) {
		this.userService = userService;
		this.productService = productService;
		this.addressService = addressService;
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
		AddressDto addressDto = addressService.findByUserNumDefault(userNum);
		String address = "";
		
		if (addressDto.getRoadAddress() == null && addressDto.getJibunAddress() != null) {
			address = addressDto.getJibunAddress();
		} else if (addressDto.getRoadAddress() != null && addressDto.getJibunAddress() == null) {
			address = addressDto.getRoadAddress();
		} else {
			address = "등록된 주소지가 없습니다.";
		}
		
		System.out.println("등록된 기본 주소지 : " + address);

		// 사용자가 가장 최근에 등록한 3개의 상품들만 조회
		List<ProductDto> recentProducts = productService.findByUserNum(userNum);

		model.addAttribute("user", userDto);
		model.addAttribute("recentProducts", recentProducts);
		model.addAttribute("address", address);
		return BASE_URL + "mypage";
	}

	@GetMapping("/api/validateUserByOAuth2/{provider}")
	public String validateUserByOAuth2(@PathVariable(value = "provider") String provider,
			@RequestParam("num") Long userNum, HttpServletRequest request) {
		request.getSession().setAttribute("redirectUrl", "/mypage/editProfile?num=" + userNum);
		return "redirect:/oauth2/authorization/" + provider;
	}

	@GetMapping("/mypage/reConfirmUserInfo")
	public String showReConfirmUserInfoPage(@RequestParam("num") Long userNum, Model model) {
		UserDto userDto = userService.findByNum(userNum);

		model.addAttribute("email", userDto.getEmail());
		model.addAttribute("provider", userDto.getProvider());
		model.addAttribute("num", userNum);
		model.addAttribute("pwd", userDto.getPwd());

		return BASE_URL + "reConfirmUserInfo";
	}

	@GetMapping("/mypage/editProfile")
	public String showEditProfilePage(@RequestParam("num") Long userNum, Model model) {
		UserDto userDto = userService.findByNum(userNum);
		List<AddressDto> addressList = addressService.findByUserNum(userNum);
		model.addAttribute("user", userDto);
		model.addAttribute("addressList", addressList);
		return BASE_URL + "editProfile";
	}

	@Deprecated
	@GetMapping("/resetPassword")
	public String showResetPasswordPage(@RequestParam("email") String userEmail, Model model) {
		Long userNum = userService.findNumByEmail(userEmail);
		UserDto userDto = userService.findByNum(userNum);

		model.addAttribute("num", userDto.getNum());

		return BASE_URL + "resetPassword";
	}

}
