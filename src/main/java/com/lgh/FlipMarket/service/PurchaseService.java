package com.lgh.FlipMarket.service;

import org.springframework.stereotype.Service;

import com.lgh.FlipMarket.dto.ProductDto;
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

}
