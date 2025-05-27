package com.lgh.StudyProject.controller.cart;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lgh.StudyProject.dto.CartDto;
import com.lgh.StudyProject.dto.ProductDto;
import com.lgh.StudyProject.dto.UserDto;
import com.lgh.StudyProject.service.*;

@Controller
public class CartController {

	private final PurchaseService purchaseService;

	private final CartService cartService;

	private final UserService userService;

	private final ProductService productService;

	private final String baseUrl = "cart/";

	public CartController(CartService cartService, UserService userService, ProductService productService,
			PurchaseService purchaseService) {
		this.cartService = cartService;
		this.userService = userService;
		this.productService = productService;
		this.purchaseService = purchaseService;
	}

	@GetMapping("/cart")
	public String cartForm(@RequestParam("num") Long userNum, Model model) {
		List<CartDto> cartItems = cartService.findAllByUserNum(userNum);

		int totalPrice = 0;
		for (int i = 0; i < cartItems.size(); i++) {
			totalPrice += cartItems.get(i).getPrice() * cartItems.get(i).getQuantity();
		}

		model.addAttribute("userNum", userNum);
		model.addAttribute("cartItems", cartItems);
		model.addAttribute("totalPrice", totalPrice);

		return baseUrl + "cart";
	}

	@ResponseBody
	@PostMapping("/api/cart/deleteCart")
	public Map<String, String> deleteCart(@RequestBody Map<String, String> data) {
		Long cartNum = Long.parseLong(data.get("num"));

		Long productNum = cartService.findByNum(cartNum).get().getProduct().getNum();
		int stock = productService.findByNum(productNum).getStock();
		int quantity = cartService.findByNum(cartNum).get().getQuantity();

		productService.updateProductStock(productNum, stock + quantity);

		int result = cartService.deleteByNum(cartNum);

		if (result > 0) {
			return Map.of("result", "0");
		} else {
			return Map.of("result", "1");
		}
	}

	@ResponseBody
	@PostMapping("/api/cart/purchase")
	public Map<String, String> purchase(@RequestBody Map<String, String> data) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		Long cartNum = Long.parseLong(data.get("cartNum"));
		Long userNum = userService.findNumByEmail(authentication.getName());
		Long productNum = cartService.findByNum(cartNum).get().getProduct().getNum();
		int totalPrice = Integer.parseInt(data.get("totalPrice"));
		int quantity = Integer.parseInt(data.get("quantity"));

		UserDto userDto = userService.findByNum(userNum);
		ProductDto productDto = productService.findByNum(productNum);

		try {
			purchaseService.addPurchase(userDto, productDto, totalPrice, quantity);
			return Map.of("result", "0");
		} catch (Exception e) {
			return Map.of("result", "1");
		}

	}

}
