package com.lgh.FlipMarket.controller.like;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lgh.FlipMarket.service.LikeService;
import com.lgh.FlipMarket.service.ProductService;

@RestController
@RequestMapping("/api")
public class LikeApiController {

	private final LikeService likeService;
	
	private final ProductService productService;

	public LikeApiController(LikeService likeService, ProductService productService) {
		this.likeService = likeService;
		this.productService = productService;
	}

	@PostMapping("/likeProduct")
	public ResponseEntity<?> likeProduct(@RequestBody Map<String, String> data) {
		Long userNum = Long.parseLong(data.get("userNum"));
		Long productNum = Long.parseLong(data.get("productNum"));
		int likeCount = Integer.parseInt(data.get("likeCount"));
		
		// 이미 좋아요 누른 상품일 경우 like_product 데이터 삭제 및 상품 좋아요 개수 -1
		if (likeService.countByUserNumAndProductNum(userNum, productNum) > 0) {
			likeService.deleteByUserNumAndProductNum(userNum, productNum);
			likeCount--;
		}
		// 좋아요 누르지 않은 상품일 경우 like_product 데이터 삽입 및 상품 좋아요 개수 +1
		else {
			likeService.saveLikeProduct(userNum, productNum);
			likeCount++;
		}
		
		// 상품의 총 개수를 수정
		productService.updateProductLikeCount(likeCount, productNum);
		
		return ResponseEntity.ok("좋아요 수정 완료");
	}

}
