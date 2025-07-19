package com.lgh.FlipMarket.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.lgh.FlipMarket.dto.ProductDto;
import com.lgh.FlipMarket.dto.PurchaseDto;
import com.lgh.FlipMarket.dto.UserDto;
import com.lgh.FlipMarket.entity.Product;
import com.lgh.FlipMarket.entity.Purchase;
import com.lgh.FlipMarket.entity.User;
import com.lgh.FlipMarket.repository.ProductRepository;
import com.lgh.FlipMarket.repository.PurchaseRepository;
import com.lgh.FlipMarket.repository.UserRepository;

@Service
public class PurchaseService {

	private final UserRepository userRepository;

	private final ProductRepository productRepository;

	private final PurchaseRepository purchaseRepository;

	public PurchaseService(UserRepository userRepository, ProductRepository productRepository,
			PurchaseRepository purchaseRepository) {
		this.userRepository = userRepository;
		this.productRepository = productRepository;
		this.purchaseRepository = purchaseRepository;
	}

	// 결제 신규 등록
	public void addPurchase(UserDto userDto, ProductDto productDto, int totalPrice, int quantity) {
		User user = userRepository.findById(userDto.getNum())
				.orElseThrow(() -> new IllegalArgumentException("해당 유저는 존재하지 않습니다."));
		Product product = productRepository.findById(productDto.getNum())
				.orElseThrow(() -> new IllegalArgumentException("해당 상품은 존재하지 않습니다."));
		Purchase purchase = new Purchase(user, product, quantity, totalPrice);

		purchaseRepository.save(purchase);
	}

	// 로그인한 사용자의 결제 내역 조회
	public List<PurchaseDto> findByUserNum(Long userNum) {
		List<Object[]> results = purchaseRepository.findByUserNum(userNum);
		User user = userRepository.findById(userNum)
				.orElseThrow(() -> new IllegalArgumentException("해당 유저는 존재하지 않습니다."));

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		return results.stream()
				.map(r -> new PurchaseDto(Long.parseLong(r[0].toString()), Integer.parseInt(r[1].toString()),
						Integer.parseInt(r[2].toString()), user,
						productRepository.findById(Long.parseLong(r[4].toString()))
								.orElseThrow(() -> new IllegalArgumentException("해당하는 상품은 존재하지 않습니다.")),
						LocalDateTime.parse(r[5].toString().split("\\.")[0], formatter),
						Integer.parseInt(r[6].toString()) == 0 ? false : true))
				.collect(Collectors.toList());
	}

	public PurchaseDto findByNum(Long purchaseNum) {
		Purchase purchase = purchaseRepository.findById(purchaseNum)
				.orElseThrow(() -> new IllegalArgumentException("해당하는 결재내역이 없습니다."));
		return new PurchaseDto(purchaseNum, purchase.getQuantity(), purchase.getTotalPrice(), null, null,
				purchase.getCreatedAt(), false);
	}

}
