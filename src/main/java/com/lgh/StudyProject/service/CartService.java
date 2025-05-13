package com.lgh.StudyProject.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.lgh.StudyProject.dto.CartDto;
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

	public List<CartDto> findAllByUserNum(Long userNum) {
		List<Object[]> results = cartRepository.findByUserNum(userNum);

		return results.stream()
				.map(r -> new CartDto(Long.parseLong(r[0].toString()), r[1].toString(), r[2].toString(),
						r[3].toString(), r[4].toString(), Integer.parseInt(r[5].toString()),
						Integer.parseInt(r[6].toString())))
				.collect(Collectors.toList());
	}

	public void addCart(UserDto userDto, ProductDto productDto, int stock) {
		User user = userRepository.findById(userDto.getNum())
				.orElseThrow(() -> new IllegalArgumentException("해당 유저는 존재하지 않습니다."));
		Product product = productRepository.findById(productDto.getNum())
				.orElseThrow(() -> new IllegalArgumentException("해당 상품은 존재하지 않습니다."));
		Cart cart = new Cart(user, product, stock);

		cartRepository.save(cart);
	}

	public int countCartByUserNumAndProductNum(Long userNum, Long productNum) {
		return cartRepository.countCartByUserNumAndProductNum(userNum, productNum);
	}
	
	public int deleteByNum(Long num) {
		return cartRepository.deleteByNum(num);
	}

}
