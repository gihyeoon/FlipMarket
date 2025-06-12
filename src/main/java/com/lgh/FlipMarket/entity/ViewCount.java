package com.lgh.FlipMarket.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "view_count")
public class ViewCount {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long num;
	
	@ManyToOne
	@JoinColumn(name = "user_num", referencedColumnName = "num")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "product_num", referencedColumnName = "num")
	private Product product;
	
	@CreationTimestamp
	private LocalDateTime viewedAt;

	public ViewCount(User user, Product product) {
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
	
	public LocalDateTime getViewedAt() {
		return viewedAt;
	}
	
}
