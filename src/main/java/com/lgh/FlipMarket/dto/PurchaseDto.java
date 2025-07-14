package com.lgh.FlipMarket.dto;

import com.lgh.FlipMarket.entity.Product;
import com.lgh.FlipMarket.entity.User;

public class PurchaseDto {

	private Long num;
	
	private int quantity;
	
	private int totalPrice;
	
	private User user;
	
	private Product product;
	
	public PurchaseDto() {
		// TODO Auto-generated constructor stub
	}

	public PurchaseDto(Long num, int quantity, int totalPrice, User user, Product product) {
		this.num = num;
		this.quantity = quantity;
		this.totalPrice = totalPrice;
		this.user = user;
		this.product = product;
	}
	
	public Long getNum() {
		return num;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public int getTotalPrice() {
		return totalPrice;
	}
	
	public User getUser() {
		return user;
	}
	
	public Product getProduct() {
		return product;
	}
	
}
