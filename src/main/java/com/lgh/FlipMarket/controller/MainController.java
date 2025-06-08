package com.lgh.FlipMarket.controller;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lgh.FlipMarket.dto.ProductDto;
import com.lgh.FlipMarket.service.LikeService;
import com.lgh.FlipMarket.service.ProductService;
import com.lgh.FlipMarket.service.UserService;

@Controller
public class MainController {

	private final UserService userService;

	private final ProductService productService;
	
	private final LikeService likeService;

	public MainController(UserService userService, ProductService productService, LikeService likeService) {
		this.userService = userService;
		this.productService = productService;
		this.likeService = likeService;
	}

	@GetMapping("/")
	public String redirectMainForm() {
		return "redirect:/main";
	}

	@GetMapping("/main")
	public String mainForm(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = "";
		Long userNum = 0L;

		if (authentication.getPrincipal() instanceof OAuth2User) {
			OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
			// 네이버 로그인일 경우
			if ((String) oauth2User.getAttributes().get("email") == null) {
				Map<String, Object> response = (Map<String, Object>) oauth2User.getAttributes().get("response");
				email = (String) response.get("email");
			}
			// 구글 로그인일 경우
			else {
				email = (String) oauth2User.getAttributes().get("email");
			}
			userNum = userService.findNumByEmail(email);
			model.addAttribute("num", userNum);
			System.out.println("소셜 로그인 이메일 : " + email);
		} else {
			email = authentication.getName();

			if (!email.equals("anonymousUser")) {
				userNum = userService.findNumByEmail(email);
				model.addAttribute("num", userNum);
			} else {
				model.addAttribute("num", "");
			}
		}

		List<ProductDto> productList;

		if (keyword != null && !keyword.isEmpty()) {
			productList = productService.findByKeywordAndUserNum("%" + keyword + "%", userNum);
		} else {
			productList = productService.findByUserNumNot(userNum);
		}

		model.addAttribute("productList", productList);
		
		// 로그인한 사용자가 좋아요 누른 상품 번호들을 View로 넘김
		List<Long> likeProductList = likeService.findByUserNum(userNum);
		
		model.addAttribute("likeProductList", likeProductList);

		return "main";
	}

}
