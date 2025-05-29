package com.lgh.StudyProject.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.lgh.StudyProject.dto.ProductDto;
import com.lgh.StudyProject.dto.UserDto;
import com.lgh.StudyProject.entity.Product;
import com.lgh.StudyProject.entity.User;
import com.lgh.StudyProject.repository.CartRepository;
import com.lgh.StudyProject.repository.ProductRepository;
import com.lgh.StudyProject.repository.UserRepository;

@Service
public class ProductService {

	private final UserRepository userRepository;

	private final ProductRepository productRepository;

	public ProductService(UserRepository userRepository, ProductRepository productRepository,
			CartRepository cartRepository) {
		this.userRepository = userRepository;
		this.productRepository = productRepository;
	}

	// 모든 상품 조회
	public List<Product> findAll() {
		return productRepository.findAll();
	}

	// 상품 상세 조회 (조건1: 상품번호)
	public ProductDto findByNum(Long num) {
		Product product = productRepository.findById(num)
				.orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다."));

		return ProductDto.builder().num(product.getNum()).productName(product.getProductName())
				.price(product.getPrice()).category(product.getCategory()).stock(product.getStock())
				.description(product.getDescription()).imagePath(product.getImagePath()).build();
	}

	public int countByProductNameAndCategory(String productName, String category, Long userNum) {
		return productRepository.countByProductNameAndCategory(productName, category, userNum);
	}

	// 메인화면 기본 조회 (조건1: 로그인한 사용자는 제외)
	public List<ProductDto> findByUserNumNot(Long userNum) {
		List<Object[]> results = productRepository.findByUserNumNot(userNum);

		return results.stream()
				.map(r -> new ProductDto(Long.parseLong(r[0].toString()), r[1].toString(),
						Integer.parseInt(r[2].toString()), r[3].toString(), Integer.parseInt(r[4].toString()),
						r[5].toString(), r[6].toString()))
				.collect(Collectors.toList());
	}

	// 메인화면 검색 조회 (조건1: 상품명, 조건2: 로그인한 사용자는 제외)
	public List<ProductDto> findByKeywordAndUserNum(String keyword, Long userNum) {
		List<Object[]> results = productRepository.findByKeywordAndUserNum(keyword, userNum);

		return results.stream()
				.map(r -> new ProductDto(Long.parseLong(r[0].toString()), r[1].toString(),
						Integer.parseInt(r[2].toString()), r[3].toString(), Integer.parseInt(r[4].toString()),
						r[5].toString(), r[6].toString()))
				.collect(Collectors.toList());
	}

	// 상품 신규 등록
	public void addProduct(String path, String productName, String category, int price, int stock, String desc,
			UserDto userDto) throws IOException {
		User user = userRepository.findById(userDto.getNum())
				.orElseThrow(() -> new IllegalArgumentException("해당 유저는 존재하지 않습니다."));

		Product product = new Product(productName, price, category, stock, desc, path, user);
		productRepository.save(product);
	}

	public int updateProductStock(Long productNum, int quantity) {
		return productRepository.updateProductStock(productNum, quantity);
	}
	
	// 로그인한 사용자가 가장 최근에 등록했던 3개의 상품들을 조회 (조건1: 로그인한 사용자)
	public List<ProductDto> findByUserNum(Long userNum) {
		List<Object[]> results = productRepository.findByUserNum(userNum);

		return results.stream()
				.map(r -> new ProductDto(Long.parseLong(r[0].toString()), r[1].toString(),
						Integer.parseInt(r[2].toString()), r[3].toString(), Integer.parseInt(r[4].toString()),
						r[5].toString(), r[6].toString()))
				.collect(Collectors.toList());
	}

}
