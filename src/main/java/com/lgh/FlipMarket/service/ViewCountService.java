package com.lgh.FlipMarket.service;

import org.springframework.stereotype.Service;

import com.lgh.FlipMarket.entity.Product;
import com.lgh.FlipMarket.entity.User;
import com.lgh.FlipMarket.entity.ViewCount;
import com.lgh.FlipMarket.repository.ProductRepository;
import com.lgh.FlipMarket.repository.UserRepository;
import com.lgh.FlipMarket.repository.ViewCountRepository;

@Service
public class ViewCountService {

	private final UserRepository userRepository;

	private final ProductRepository productRepository;

	private final ViewCountRepository viewCountRepository;

	public ViewCountService(UserRepository userRepository, ProductRepository productRepository,
			ViewCountRepository viewCountRepository) {
		this.userRepository = userRepository;
		this.productRepository = productRepository;
		this.viewCountRepository = viewCountRepository;
	}

	// 조회수 증가
	public void save(Long userNum, Long productNum) {
		User user = userRepository.findById(userNum).orElseThrow(() -> new IllegalArgumentException("해당하는 유저가 없습니다."));
		Product product = productRepository.findById(productNum)
				.orElseThrow(() -> new IllegalArgumentException("해당하는 상품이 없습니다."));

		viewCountRepository.save(new ViewCount(user, product));
	}

}
