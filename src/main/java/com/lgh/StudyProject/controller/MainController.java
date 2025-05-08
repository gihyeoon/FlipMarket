package com.lgh.StudyProject.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.lgh.StudyProject.entity.Product;
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
	public String mainForm(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String id = authentication.getName();
		if (!id.equals("anonymousUser")) {
			Long num = userService.findNumById(id);
			model.addAttribute("num", num);
		} else {
			model.addAttribute("num", "");
		}

		List<Product> productList = productService.findAll();

		model.addAttribute("productList", productList);

		return "main";
	}

}
