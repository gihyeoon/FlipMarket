package com.lgh.StudyProject.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "product")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long num;

	@Column(nullable = false)
	private String productName;
	
	@Column(nullable = false)
	private int price;
	
	@Column(nullable = false)
	private String category;
	
	private String description;
	
	private String imagePath;
	
	@ManyToOne
	@JoinColumn(name = "")
	private User user;
	
	@CreationTimestamp
	private LocalDateTime createdAt;
	
	@UpdateTimestamp
	private LocalDateTime updatedAt;
	
	public Product() {
	}
	
	public Product(String productName, int price, String category, String description, String imagePath, User user) {
		this.productName = productName;
		this.price = price;
		this.category = category;
		this.description = description;
		this.imagePath = imagePath;
		this.user = user;
	}
	
	public Long getNum() {
		return num;
	}
	
	public String getProductName() {
		return productName;
	}

	public int getPrice() {
		return price;
	}

	public String getCategory() {
		return category;
	}

	public String getDescription() {
		return description;
	}

	public String getImagePath() {
		return imagePath;
	}
	
}
