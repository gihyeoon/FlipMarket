package com.lgh.FlipMarket.dto;

public class CartDto {

	private Long num;

	private String productName;

	private String category;

	private String description;

	private String imagePath;

	private int price;

	private int quantity;
	
	private int stock;

	public CartDto() {
		// TODO Auto-generated constructor stub
	}

	public CartDto(Long num, String productName, String category, String description, String imagePath, int price,
			int quantity, int stock) {
		this.num = num;
		this.productName = productName;
		this.category = category;
		this.description = description;
		this.imagePath = imagePath;
		this.price = price;
		this.quantity = quantity;
		this.stock = stock;
	}

	public Long getNum() {
		return num;
	}

	public String getProductName() {
		return productName;
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

	public int getPrice() {
		return price;
	}

	public int getQuantity() {
		return quantity;
	}
	
	public int getStock() {
		return stock;
	}

}
