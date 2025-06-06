package com.lgh.FlipMarket.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lgh.FlipMarket.entity.Like;
import com.lgh.FlipMarket.entity.Product;
import com.lgh.FlipMarket.entity.User;
import com.lgh.FlipMarket.repository.LikeRepository;
import com.lgh.FlipMarket.repository.ProductRepository;
import com.lgh.FlipMarket.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class LikeService {

	private final LikeRepository likeRepository;

	private final UserRepository userRepository;

	private ProductRepository productRepository;

	public LikeService(LikeRepository likeRepository, UserRepository userRepository,
			ProductRepository productRepository) {
		this.likeRepository = likeRepository;
		this.userRepository = userRepository;
		this.productRepository = productRepository;
	}

	// 로그인한 사용자가 좋아요를 누른 상품 번호만 가져옴
	public List<String> findByUserNum(Long userNum) {
		List<String> likeProductList = likeRepository.findByUserNum(userNum);
		return likeProductList;
	}

	// 이미 사용자가 좋아요 누른 상품이 있는지 확인 (조건1: 로그인 사용자 번호, 조건2: 상품 번호)
	public int countByUserNumAndProductNum(Long userNum, Long productNum) {
		return likeRepository.countByUserNumAndProductNum(userNum, productNum);
	}

	// 해당 상품 좋아요 취소
	@Transactional
	public void deleteByUserNumAndProductNum(Long userNum, Long productNum) {
		Long num = likeRepository.findByUserNumAndProductNum(userNum, productNum);
		likeRepository.deleteById(num);
	}

	// 해당 상품 좋아요 추가
	public void saveLikeProduct(Long userNum, Long productNum) {
		User user = userRepository.findById(userNum).orElseThrow(() -> new IllegalArgumentException("해당하는 유저가 없습니다."));
		Product product = productRepository.findById(productNum)
				.orElseThrow(() -> new IllegalArgumentException("해당하는 상품이 없습니다."));

		likeRepository.save(new Like(user, product));
	}

}
