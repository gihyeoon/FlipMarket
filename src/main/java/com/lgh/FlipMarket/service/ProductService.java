package com.lgh.FlipMarket.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.lgh.FlipMarket.dto.ProductDto;
import com.lgh.FlipMarket.dto.UserDto;
import com.lgh.FlipMarket.entity.Product;
import com.lgh.FlipMarket.entity.User;
import com.lgh.FlipMarket.repository.CartRepository;
import com.lgh.FlipMarket.repository.ProductRepository;
import com.lgh.FlipMarket.repository.UserRepository;

import jakarta.transaction.Transactional;

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
				.description(product.getDescription()).imagePath(product.getImagePath())
				.likeCount(product.getLikeCount()).build();
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
						r[5].toString(), r[6].toString(), Integer.parseInt(r[7].toString())))
				.collect(Collectors.toList());
	}

	// 메인화면 검색 조회 (조건1: 상품명, 조건2: 로그인한 사용자는 제외)
	public List<ProductDto> findByKeywordAndUserNum(String keyword, Long userNum) {
		List<Object[]> results = productRepository.findByKeywordAndUserNum(keyword, userNum);

		return results.stream()
				.map(r -> new ProductDto(Long.parseLong(r[0].toString()), r[1].toString(),
						Integer.parseInt(r[2].toString()), r[3].toString(), Integer.parseInt(r[4].toString()),
						r[5].toString(), r[6].toString(), Integer.parseInt(r[7].toString())))
				.collect(Collectors.toList());
	}

	// 상품 신규 등록
	public void addProduct(String path, String productName, String category, int price, int stock, String desc,
			UserDto userDto) throws IOException {
		User user = userRepository.findById(userDto.getNum())
				.orElseThrow(() -> new IllegalArgumentException("해당 유저는 존재하지 않습니다."));

		Product product = new Product(productName, price, category, stock, desc, path, 0, user);
		productRepository.save(product);
	}

	// 상품의 재고 수량을 업데이트 => 사용자가 해당 상품을 구매할 경우 해당 상품의 재고수량을 마이너스한다.
	@Transactional
	public int updateProductStock(Long productNum, int quantity) {
		return productRepository.updateProductStock(productNum, quantity);
	}

	// 로그인한 사용자가 가장 최근에 등록했던 3개의 상품들을 조회 (조건1: 로그인한 사용자)
	public List<ProductDto> findByUserNum(Long userNum) {
		List<Object[]> results = productRepository.findByUserNum(userNum);

		return results.stream()
				.map(r -> new ProductDto(Long.parseLong(r[0].toString()), r[1].toString(),
						Integer.parseInt(r[2].toString()), r[3].toString(), Integer.parseInt(r[4].toString()),
						r[5].toString(), r[6].toString(), Integer.parseInt(r[7].toString())))
				.collect(Collectors.toList());
	}

	// 상품 수정 화면에서 해당 상품을 수정
	@Transactional
	public int updateProduct(String imagePath, String productName, String category, int stock, int price,
			String description, Long productNum) {
		return productRepository.updateProduct(imagePath, productName, category, stock, price, description, productNum);
	}

	// 상품 삭제
	@Transactional
	public void deleteProduct(Long productNum) {
		productRepository.deleteById(productNum);
	}

	// 상품의 좋아요 개수 수정
	@Transactional
	public int updateProductLikeCount(int likeCount, Long productNum) {
		return productRepository.updateProductLikeCount(likeCount, productNum);
	}

}
