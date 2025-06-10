package com.lgh.FlipMarket.controller.product;

import java.io.IOException;
import java.util.Map;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lgh.FlipMarket.config.AuthenticationUserId;
import com.lgh.FlipMarket.config.ProductImageHandler;
import com.lgh.FlipMarket.dto.ProductDto;
import com.lgh.FlipMarket.dto.UserDto;
import com.lgh.FlipMarket.service.CartService;
import com.lgh.FlipMarket.service.ProductService;
import com.lgh.FlipMarket.service.UserService;

@RestController
@RequestMapping("/api")
public class ProductApiController {

	private ProductImageHandler productImageHandler = new ProductImageHandler();

	private final ProductService productService;

	private final UserService userService;

	private final CartService cartService;

	private final AuthenticationUserId authenticationUserId;

	public ProductApiController(ProductService productService, UserService userService, CartService cartService,
			AuthenticationUserId authenticationUserId) {
		this.productService = productService;
		this.userService = userService;
		this.cartService = cartService;
		this.authenticationUserId = authenticationUserId;
	}

	@PostMapping("/addProduct")
	public Map<String, String> addProduct(@RequestParam("image") MultipartFile image,
			@RequestParam("productName") String productName, @RequestParam("category") String category,
			@RequestParam("stock") int stock, @RequestParam("price") int price, @RequestParam("desc") String desc)
			throws IOException {
		Long userNum = authenticationUserId.getUserNum();
		UserDto userDto = userService.findByNum(userNum);

		// 로그인한 사용자가 상품명과 카테고리로 이미 등록되어있는 상품이 있는지 확인한다.
		int result = productService.countByProductNameAndCategory(productName, category, userNum);

		if (result > 0) {
			return Map.of("result", "1");
		} else {
			productService.addProduct(productImageHandler.save(image), productName, category, price, stock, desc,
					userDto);
			return Map.of("result", "0");
		}
	}

	@PostMapping("/addCart")
	public Map<String, String> addCart(@RequestBody Map<String, String> data) {
		Long userNum = authenticationUserId.getUserNum();
		Long productNum = Long.parseLong(data.get("productNum"));
		int count = cartService.countCartByUserNumAndProductNum(userNum, productNum);

		if (userNum == 0) {
			return Map.of("result", "0");
		}

		if (count > 0) {
			return Map.of("result", "2");
		} else {
			UserDto userDto = userService.findByNum(userNum);
			ProductDto productDto = productService.findByNum(productNum);
			int quantity = Integer.parseInt(data.get("quantity"));

			cartService.addCart(userDto, productDto, quantity);

			return Map.of("result", "1");
		}
	}

	@PostMapping("/editProduct")
	public Map<String, String> editProduct(@RequestParam(value = "image", required = false) MultipartFile image,
			@RequestParam("num") Long productNum, @RequestParam("productName") String productName,
			@RequestParam("category") String category, @RequestParam("stock") int stock,
			@RequestParam("price") int price, @RequestParam("desc") String desc) throws IOException {
		// 상품 이미지를 수정하지 않았을 경우
		if (image == null) {
			String originImagePath = productService.findByNum(productNum).getImagePath();
			productService.updateProduct(originImagePath, productName, category, stock, price, desc, productNum);
		} else {
			productService.updateProduct(productImageHandler.save(image), productName, category, stock, price, desc,
					productNum);
		}

		return Map.of("result", "0");
	}

	@PostMapping("/deleteProduct")
	public Map<String, String> deleteProduct(@RequestBody Map<String, String> data) {
		Long productNum = Long.parseLong(data.get("num"));
		productService.deleteProduct(productNum);
		return Map.of("result", "0");
	}

}
