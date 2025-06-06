package com.lgh.FlipMarket.dto;

import com.lgh.FlipMarket.entity.Product;
import com.lgh.FlipMarket.entity.User;

public class LikeDto {

	private Long num;
	
	private User user;
	
	private Product product;
	
	public LikeDto() {
		// TODO Auto-generated constructor stub
	}

	public LikeDto(Long num, User user, Product product) {
		this.num = num;
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
