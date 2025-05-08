package com.lgh.StudyProject.service;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.lgh.StudyProject.dto.ProductDto;
import com.lgh.StudyProject.entity.Product;
import com.lgh.StudyProject.entity.User;
import com.lgh.StudyProject.repository.ProductRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductService {

	private final ProductRepository productRepository;

	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public ProductDto findByNum(Long num) {
		Product product = productRepository.findById(num)
				.orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다."));
		
		return ProductDto.builder()
				.num(product.getNum())
				.productName(product.getProductName())
				.price(product.getPrice())
				.category(product.getCategory())
				.description(product.getDescription())
				.imagePath(product.getImagePath())
				.build();
	}

	public void addProduct(String path, String productName, String category, int price, String desc, User user)
			throws IOException {
		Product product = new Product(productName, price, category, desc, path, user);
		productRepository.save(product);
	}

}
