package com.lgh.StudyProject.controller.product;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lgh.StudyProject.dto.ProductDto;
import com.lgh.StudyProject.service.ProductService;

@Controller
public class ProductController {

	private final ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping("/addProduct")
	public String addProductForm() {
		return "addProduct";
	}

	@GetMapping("/products")
	public String getProductDetail(@RequestParam("num") Long num, Model model) {
		ProductDto product = productService.findByNum(num);
		model.addAttribute("product", product);
		return "product/productDetail";
	}

}
