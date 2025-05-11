package com.lgh.StudyProject.service;

import org.springframework.stereotype.Service;

import com.lgh.StudyProject.dto.ProductDto;
import com.lgh.StudyProject.dto.UserDto;
import com.lgh.StudyProject.entity.Cart;
import com.lgh.StudyProject.entity.Product;
import com.lgh.StudyProject.entity.User;
import com.lgh.StudyProject.repository.CartRepository;
import com.lgh.StudyProject.repository.ProductRepository;
import com.lgh.StudyProject.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CartService {

	private final CartRepository cartRepository;

	private final UserRepository userRepository;

	private final ProductRepository productRepository;

	public CartService(CartRepository cartRepository, UserRepository userRepository,
			ProductRepository productRepository) {
		this.cartRepository = cartRepository;
		this.userRepository = userRepository;
		this.productRepository = productRepository;
	}

	public void addCart(UserDto userDto, ProductDto productDto, int quantity) {
		User user = userRepository.findById(userDto.getNum())
				.orElseThrow(() -> new IllegalArgumentException("해당 유저는 존재하지 않습니다."));
		Product product = productRepository.findById(productDto.getNum())
				.orElseThrow(() -> new IllegalArgumentException("해당 상품은 존재하지 않습니다."));
		Cart cart = new Cart(user, product, quantity);

		cartRepository.save(cart);
	}

	public int countCartByUserNumAndProductNum(Long userNum, Long productNum) {
		return cartRepository.countCartByUserNumAndProductNum(userNum, productNum);
	}

}
