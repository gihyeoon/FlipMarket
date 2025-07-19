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
@Table(name = "purchase")
public class Purchase {

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
	private int quantity;
	
	@Column(nullable = false)
	private int totalPrice;
	
	@CreationTimestamp
	private LocalDateTime createdAt;
	
	@UpdateTimestamp
	private LocalDateTime updatedAt;
	
	public Purchase() {
		// TODO Auto-generated constructor stub
	}
	
	public Purchase(User user, Product product, int quantity, int totalPrice) {
		this.user = user;
		this.product = product;
		this.quantity = quantity;
		this.totalPrice = totalPrice;
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
	
	public int getQuantity() {
		return quantity;
	}
	
	public int getTotalPrice() {
		return totalPrice;
	}
	
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	
}
