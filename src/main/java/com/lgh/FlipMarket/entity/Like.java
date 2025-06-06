package com.lgh.FlipMarket.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "like_product")
public class Like {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long num;
	
	@ManyToOne
	@JoinColumn(name = "user_num", referencedColumnName = "num")
	private User user;

	@ManyToOne
	@JoinColumn(name = "product_num", referencedColumnName = "num")
	private Product product;
	
	public Like() {
		// TODO Auto-generated constructor stub
	}

	public Like(User user, Product product) {
		this.user = user;
		this.product = product;
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
	
}
