package com.lgh.FlipMarket.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "review")
public class Review {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long num;
	
	@ManyToOne
	@JoinColumn(name = "user_num", referencedColumnName = "num")
	private User user;

	@ManyToOne
	@JoinColumn(name = "product_num", referencedColumnName = "num")
	private Product product;
	
	@Column(nullable = false)
	private int rating;
	
	@Column(nullable = true)
	private String description;
	
	@CreationTimestamp
	private LocalDateTime createdAt;
	
	@UpdateTimestamp
	private LocalDateTime updatedAt;
	
	public Review() {
		// TODO Auto-generated constructor stub
	}

	public Review(User user, Product product, int rating, String description) {
		this.user = user;
		this.product = product;
		this.rating = rating;
		this.description = description;
	}
	
	public Long getNum() {
		return num;
	}
	
	public User getUser() {
		return user;
	}
	
	public Product getProduct() {
		return product;
	}
	
	public int getRating() {
		return rating;
	}
	
	public String getDescription() {
		return description;
	}

}
