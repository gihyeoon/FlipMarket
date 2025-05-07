package com.lgh.StudyProject.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.lgh.StudyProject.entity.Product;
import com.lgh.StudyProject.repository.ProductRepository;
import com.lgh.StudyProject.repository.UserRepository;

@Controller
public class MainController {
	
	private final UserRepository userRepository;
	
	private final ProductRepository productRepository;
	
	public MainController(UserRepository userRepository, ProductRepository productRepository) {
		this.userRepository = userRepository;
		this.productRepository = productRepository;
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
			Long num = userRepository.findById(id).get().getNum();
			model.addAttribute("num", num);
		} else {
			model.addAttribute("num", "");
		}
		
		List<Product> productList = productRepository.findAll();
		
		model.addAttribute("productList", productList);
		
		return "main";
	}

}
