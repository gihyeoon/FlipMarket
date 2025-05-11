package com.lgh.StudyProject.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lgh.StudyProject.dto.ProductDto;
import com.lgh.StudyProject.dto.UserDto;
import com.lgh.StudyProject.entity.Product;
import com.lgh.StudyProject.entity.User;
import com.lgh.StudyProject.repository.CartRepository;
import com.lgh.StudyProject.repository.ProductRepository;
import com.lgh.StudyProject.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductService {

	private final UserRepository userRepository;

	private final ProductRepository productRepository;

	public ProductService(UserRepository userRepository, ProductRepository productRepository,
			CartRepository cartRepository) {
		this.userRepository = userRepository;
		this.productRepository = productRepository;
	}

	public List<Product> findAll() {
		return productRepository.findAll();
	}

	public ProductDto findByNum(Long num) {
		Product product = productRepository.findById(num)
				.orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다."));

		return ProductDto.builder().num(product.getNum()).productName(product.getProductName())
				.price(product.getPrice()).category(product.getCategory()).quantity(product.getQuantity())
				.description(product.getDescription()).imagePath(product.getImagePath()).build();
	}

	public void addProduct(String path, String productName, String category, int price, int quantity, String desc,
			UserDto userDto) throws IOException {
		User user = userRepository.findById(userDto.getNum())
				.orElseThrow(() -> new IllegalArgumentException("해당 유저는 존재하지 않습니다."));

		Product product = new Product(productName, price, category, quantity, desc, path, user);
		productRepository.save(product);
	}

}
