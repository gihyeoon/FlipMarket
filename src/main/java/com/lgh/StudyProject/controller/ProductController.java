package com.lgh.StudyProject.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.lgh.StudyProject.config.ProductImageHandler;
import com.lgh.StudyProject.dto.ProductDto;
import com.lgh.StudyProject.entity.User;
import com.lgh.StudyProject.repository.UserRepository;
import com.lgh.StudyProject.service.ProductService;

@Controller
public class ProductController {

	private ProductImageHandler productImageHandler = new ProductImageHandler();

	private final ProductService productService;

	private final UserRepository userRepository;

	public ProductController(ProductService productService, UserRepository userRepository) {
		this.productService = productService;
		this.userRepository = userRepository;
	}

	@GetMapping("/addProduct")
	public String addProductForm() {
		return "addProduct";
	}

	@ResponseBody
	@PostMapping("/api/addProduct")
	public ResponseEntity<String> addProduct(@RequestParam("image") MultipartFile image,
			@RequestParam("productName") String productName, @RequestParam("category") String category,
			@RequestParam("price") int price, @RequestParam("desc") String desc) throws IOException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Long num = userRepository.findById(authentication.getName()).get().getNum();
		User user = userRepository.findById(num).orElseThrow(() -> new RuntimeException("User not found"));
		try {
			productService.addProduct(productImageHandler.save(image), productName, category, price, desc, user);
			return ResponseEntity.ok("success");
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("product add fail: " + e.getMessage());
		}
	}

	@GetMapping("/products")
	public String getProductDetail(@RequestParam("num") Long num, Model model) {
		ProductDto product = productService.findByNum(num);
		model.addAttribute("product", product);
		return "product/productDetail";
	}

}
