package com.lgh.FlipMarket.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lgh.FlipMarket.config.AuthenticationUserId;
import com.lgh.FlipMarket.dto.ProductDto;
import com.lgh.FlipMarket.service.LikeService;
import com.lgh.FlipMarket.service.ProductService;

@Controller
public class MainController {

	private final ProductService productService;

	private final LikeService likeService;

	private final AuthenticationUserId authenticationUserId;

	public MainController(ProductService productService, LikeService likeService,
			AuthenticationUserId authenticationUserId) {
		this.productService = productService;
		this.likeService = likeService;
		this.authenticationUserId = authenticationUserId;
	}

	@GetMapping("/")
	public String redirectMainForm() {
		return "redirect:/main";
	}

	@GetMapping("/main")
	public String mainForm(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
		Long userNum = authenticationUserId.getUserNum();

		List<ProductDto> productList;
		if (keyword != null && !keyword.isEmpty()) {
			productList = productService.findByKeywordAndUserNum("%" + keyword + "%", userNum);
		} else {
			productList = productService.findByUserNumNot(userNum);
		}

		// 로그인한 사용자가 좋아요 누른 상품 번호들을 View로 넘김
		List<Long> likeProductList = likeService.findByUserNum(userNum);

		model.addAttribute("num", userNum);
		model.addAttribute("likeProductList", likeProductList);
		model.addAttribute("productList", productList);

		return "main";
	}

}
