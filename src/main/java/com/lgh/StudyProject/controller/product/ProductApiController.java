package com.lgh.StudyProject.controller.product;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lgh.StudyProject.config.ProductImageHandler;
import com.lgh.StudyProject.dto.ProductDto;
import com.lgh.StudyProject.dto.UserDto;
import com.lgh.StudyProject.service.CartService;
import com.lgh.StudyProject.service.ProductService;
import com.lgh.StudyProject.service.UserService;

@RestController
@RequestMapping("/api")
public class ProductApiController {

	private ProductImageHandler productImageHandler = new ProductImageHandler();

	private final ProductService productService;

	private final UserService userService;

	private final CartService cartService;

	public ProductApiController(ProductService productService, UserService userService, CartService cartService) {
		this.productService = productService;
		this.userService = userService;
		this.cartService = cartService;
	}

	@PostMapping("/addProduct")
	public ResponseEntity<String> addProduct(@RequestParam("image") MultipartFile image,
			@RequestParam("productName") String productName, @RequestParam("category") String category,
			@RequestParam("stock") int stock, @RequestParam("price") int price, @RequestParam("desc") String desc)
			throws IOException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Long num = userService.findNumByEmail(authentication.getName());
		UserDto userDto = userService.findByNum(num);
		try {
			productService.addProduct(productImageHandler.save(image), productName, category, price, stock, desc,
					userDto);
			return ResponseEntity.ok("success");
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("product add fail: " + e.getMessage());
		}
	}

	@PostMapping("/addCart")
	public Map<String, String> addCart(@RequestBody Map<String, String> data) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication.getName().equals("anonymousUser")) {
			return Map.of("result", "0");
		} else {
			Long productNum = Long.parseLong(data.get("productNum"));
			Long userNum = userService.findNumByEmail(authentication.getName());

			int count = cartService.countCartByUserNumAndProductNum(userNum, productNum);

			if (count > 0) {
				return Map.of("result", "2");
			} else {
				UserDto userDto = userService.findByNum(userNum);
				ProductDto productDto = productService.findByNum(productNum);
				int stock = Integer.parseInt(data.get("stock"));

				cartService.addCart(userDto, productDto, stock);

				return Map.of("result", "1");
			}
		}
	}

}
