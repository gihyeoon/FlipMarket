package com.lgh.StudyProject.service;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lgh.StudyProject.config.ProductImageHandler;
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

	public void addProduct(String path, String productName, String category, int price, String desc, User user)
			throws IOException {
		Product product = new Product(productName, price, category, desc, path, user);
		productRepository.save(product);
	}

}
