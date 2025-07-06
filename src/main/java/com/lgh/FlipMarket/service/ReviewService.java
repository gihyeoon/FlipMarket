package com.lgh.FlipMarket.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.lgh.FlipMarket.dto.ReviewDto;
import com.lgh.FlipMarket.repository.ReviewRepository;
import com.lgh.FlipMarket.repository.UserRepository;

@Service
public class ReviewService {

	private final ReviewRepository reviewRepository;

	private final UserRepository userRepository;

	public ReviewService(ReviewRepository reviewRepository, UserRepository userRepository) {
		this.reviewRepository = reviewRepository;
		this.userRepository = userRepository;
	}

	// 해당 상품의 리뷰들을 조회
	public List<ReviewDto> findByProductNum(Long productNum) {
		List<Object[]> results = reviewRepository.findByProductNum(productNum);

		return results.stream()
				.map(r -> new ReviewDto(Long.parseLong(r[0].toString()),
						userRepository.findById(Long.parseLong(r[1].toString())).get().getName(),
						Integer.parseInt(r[3].toString()), r[4].toString(), LocalDateTime.parse(r[5].toString())))
				.collect(Collectors.toList());
	}

}
