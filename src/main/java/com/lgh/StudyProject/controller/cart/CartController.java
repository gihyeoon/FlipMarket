package com.lgh.StudyProject.controller.cart;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lgh.StudyProject.dto.CartDto;
import com.lgh.StudyProject.service.CartService;

@Controller
public class CartController {

	private final CartService cartService;

	private final String baseUrl = "cart/";

	public CartController(CartService cartService) {
		this.cartService = cartService;
	}

	@GetMapping("/cart")
	public String cartForm(@RequestParam("num") Long userNum, Model model) {
		List<CartDto> cartItems = cartService.findAllByUserNum(userNum);

		model.addAttribute("userNum", userNum);
		model.addAttribute("cartItems", cartItems);

		return baseUrl + "cart";
	}

}
