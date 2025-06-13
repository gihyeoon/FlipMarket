package com.lgh.FlipMarket.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
	public String mainForm(Model model) {
		Long userNum = authenticationUserId.getUserNum();

		// 좋아요 수가 많은 순으로 TOP 30 제품을 조회
		List<ProductDto> top30LikeProductList = productService.findTop30LikeByUserNum(userNum);
		
		// 조회 수가 많은 순으로 TOP 30 제품을 조회
		List<ProductDto> top30ViewCountProductList = productService.findTop30ViewCountByUserNum(userNum);
		
		// 로그인한 사용자가 좋아요 누른 상품 번호들을 View로 넘김
		List<Long> likeProductList = likeService.findByUserNum(userNum);

		model.addAttribute("userNum", userNum);
		model.addAttribute("likeProductList", likeProductList);
		model.addAttribute("top30LikeProductList", top30LikeProductList);
		model.addAttribute("top30ViewCountProductList", top30ViewCountProductList);

		return "main";
	}
	
	@GetMapping("/search")
	public String searchForm(Model model) {
		
		
		return "search";
	}

}
