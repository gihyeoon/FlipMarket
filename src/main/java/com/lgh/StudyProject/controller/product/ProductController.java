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

	private static final String BASE_URL = "product/";

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping("/addProduct")
	public String showAddProductPage() {
		return BASE_URL + "addProduct";
	}

	@GetMapping("/products")
	public String showProductDetailPage(@RequestParam("num") Long productNum, Model model) {
		ProductDto product = productService.findByNum(productNum);
		model.addAttribute("product", product);
		return BASE_URL + "productDetail";
	}
	
	@GetMapping("/products/editProduct")
	public String showEditProductPage(@RequestParam("num") Long productNum, Model model) {
		ProductDto productDto = productService.findByNum(productNum);
		model.addAttribute("product", productDto);
		return BASE_URL + "editProduct";
	}

}
