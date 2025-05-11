package com.lgh.StudyProject.controller.cart;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lgh.StudyProject.service.CartService;

@Controller
public class CartController {
	
	private final CartService cartService;
	
	public CartController(CartService cartService) {
		this.cartService = cartService;
	}

	@GetMapping("/cart")
	public String cartForm(@RequestParam("num") Long userNum, Model model) {
		model.addAttribute("userNum", userNum);
		
		return "cart";
	}
	
}
