package com.lgh.FlipMarket.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lgh.FlipMarket.dto.ProductDto;
import com.lgh.FlipMarket.service.ProductService;
import com.lgh.FlipMarket.service.UserService;

@Controller
public class MainController {

	private final UserService userService;

	private final ProductService productService;

	public MainController(UserService userService, ProductService productService) {
		this.userService = userService;
		this.productService = productService;
	}

	@GetMapping("/")
	public String redirectMainForm() {
		return "redirect:/main";
	}

	@GetMapping("/main")
	public String mainForm(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = "";
		Long userNum = 0L;

		if (authentication.getPrincipal() instanceof OAuth2User) {
			OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
			email = (String) oauth2User.getAttributes().get("email");
			userNum = userService.findNumByEmail(email);
			model.addAttribute("num", userNum);
			System.out.println("소셜 로그인 이메일 : " + email);
		} else {
			email = authentication.getName();

			if (!email.equals("anonymousUser")) {
				userNum = userService.findNumByEmail(email);
				model.addAttribute("num", userNum);
			} else {
				model.addAttribute("num", "");
			}
		}

		List<ProductDto> productList;

		if (keyword != null && !keyword.isEmpty()) {
			productList = productService.findByKeywordAndUserNum("%" + keyword + "%", userNum);
		} else {
			productList = productService.findByUserNumNot(userNum);
		}

		model.addAttribute("productList", productList);

		return "main";
	}

}
