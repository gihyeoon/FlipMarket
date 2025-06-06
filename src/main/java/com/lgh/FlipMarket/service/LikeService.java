package com.lgh.FlipMarket.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lgh.FlipMarket.repository.LikeRepository;

@Service
public class LikeService {

	private final LikeRepository likeRepository;

	public LikeService(LikeRepository likeRepository) {
		this.likeRepository = likeRepository;
	}

	// 로그인한 사용자가 좋아요를 누른 상품 번호만 가져옴
	public List<String> findByUserNum(Long userNum) {
		List<String> likeProductList = likeRepository.findByUserNum(userNum);
		return likeProductList;
	}

}
