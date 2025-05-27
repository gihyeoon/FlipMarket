package com.lgh.StudyProject.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lgh.StudyProject.dto.ProductDto;
import com.lgh.StudyProject.service.ProductService;
import com.lgh.StudyProject.service.UserService;

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
		String email = authentication.getName();
		Long userNum = 0L;

		if (!email.equals("anonymousUser")) {
			userNum = userService.findNumByEmail(email);
			model.addAttribute("num", userNum);
		} else {
			model.addAttribute("num", "");
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
