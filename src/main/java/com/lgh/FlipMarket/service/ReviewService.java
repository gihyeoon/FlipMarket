package com.lgh.FlipMarket.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lgh.FlipMarket.dto.ReviewDto;
import com.lgh.FlipMarket.entity.Product;
import com.lgh.FlipMarket.entity.Review;
import com.lgh.FlipMarket.entity.User;
import com.lgh.FlipMarket.repository.ProductRepository;
import com.lgh.FlipMarket.repository.ReviewRepository;
import com.lgh.FlipMarket.repository.UserRepository;

@Service
public class ReviewService {

	private final ReviewRepository reviewRepository;

	private final UserRepository userRepository;

	private final ProductRepository productRepository;

	public ReviewService(ReviewRepository reviewRepository, UserRepository userRepository,
			ProductRepository productRepository) {
		this.reviewRepository = reviewRepository;
		this.userRepository = userRepository;
		this.productRepository = productRepository;
	}

	// 해당 상품의 리뷰들을 조회
	public List<ReviewDto> findByProductNum(Long productNum) {
		List<Object[]> results = reviewRepository.findByProductNum(productNum);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		return results.stream()
				.map(r -> new ReviewDto(Long.parseLong(r[0].toString()),
						userRepository.findById(Long.parseLong(r[1].toString())).get().getName(),
						Integer.parseInt(r[3].toString()), r[4].toString(),
						LocalDateTime.parse(r[5].toString().split("\\.")[0], formatter)))
				.collect(Collectors.toList());
	}

	public void addReview(Long userNum, Long productNum, int rating, String description) {
		User user = userRepository.findById(userNum).orElseThrow(() -> new UsernameNotFoundException("해당하는 유저가 없습니다."));
		Product product = productRepository.findById(productNum)
				.orElseThrow(() -> new IllegalArgumentException("해당하는 상품이 없습니다."));
		reviewRepository.save(new Review(user, product, rating, description));
	}

}
