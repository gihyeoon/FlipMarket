package com.lgh.FlipMarket.controller.product;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lgh.FlipMarket.config.AuthenticationUserId;
import com.lgh.FlipMarket.dto.ProductDto;
import com.lgh.FlipMarket.dto.ReviewDto;
import com.lgh.FlipMarket.service.LikeService;
import com.lgh.FlipMarket.service.ProductService;
import com.lgh.FlipMarket.service.ReviewService;

@Controller
public class ProductController {

	private final ProductService productService;

	private final LikeService likeService;

	private final AuthenticationUserId authenticationUserId;

	private final ReviewService reviewService;

	private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

	private static final String BASE_URL = "product/";

	public ProductController(ProductService productService, LikeService likeService,
			AuthenticationUserId authenticationUserId, ReviewService reviewService) {
		this.productService = productService;
		this.likeService = likeService;
		this.authenticationUserId = authenticationUserId;
		this.reviewService = reviewService;
	}

	@GetMapping("/addProduct")
	public String showAddProductPage(Model model) {
		Long userNum = authenticationUserId.getUserNum();
		model.addAttribute("userNum", userNum);
		return BASE_URL + "addProduct";
	}

	@GetMapping("/products")
	public String showProductDetailPage(@RequestParam("num") Long productNum, Model model) {
		Long userNum = authenticationUserId.getUserNum();

		ProductDto product = productService.findByNum(productNum);
		// 로그인한 사용자가 좋아요 누른 상품 번호들을 View로 넘김
		List<Long> likeProductList = likeService.findByUserNum(userNum);
		List<ProductDto> relatedProductList = productService.findTop10RelatedProductsByUserNumAndCategory(userNum,
				product.getNum(), product.getCategory());
		List<ReviewDto> reviewList = reviewService.findByProductNum(productNum);

		log.info("상품 카테고리 : " + product.getCategory());

		model.addAttribute("likeProductList", likeProductList);
		model.addAttribute("relatedProductList", relatedProductList);
		model.addAttribute("reviewList", reviewList);
		model.addAttribute("product", product);
		model.addAttribute("userNum", userNum);

		return BASE_URL + "productDetail";
	}

	@GetMapping("/products/editProduct")
	public String showEditProductPage(@RequestParam("num") Long productNum, Model model) {
		ProductDto productDto = productService.findByNum(productNum);
		model.addAttribute("product", productDto);
		return BASE_URL + "editProduct";
	}

}
