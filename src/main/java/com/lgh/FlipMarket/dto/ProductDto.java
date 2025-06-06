package com.lgh.FlipMarket.dto;

public class ProductDto {

	private Long num;

	private String productName;

	private int price;

	private String category;

	private int stock;

	private String description;

	private String imagePath;
	
	private int likeCount = 0;

	public ProductDto() {
		// TODO Auto-generated constructor stub
	}

	public ProductDto(Long num, String productName, int price, String category, int stock, String description,
			String imagePath, int likeCount) {
		this.num = num;
		this.productName = productName;
		this.price = price;
		this.category = category;
		this.stock = stock;
		this.description = description;
		this.imagePath = imagePath;
		this.likeCount = likeCount;
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

	public int getStock() {
		return stock;
	}

	public String getDescription() {
		return description;
	}

	public String getImagePath() {
		return imagePath;
	}
	
	public int getLikeCount() {
		return likeCount;
	}

	public static class Builder {
		private Long num;
		private String productName;
		private int price;
		private String category;
		private int stock;
		private String description;
		private String imagePath;
		private int likeCount;

		public Builder num(Long num) {
			this.num = num;
			return this;
		}

		public Builder productName(String productName) {
			this.productName = productName;
			return this;
		}

		public Builder price(int price) {
			this.price = price;
			return this;
		}

		public Builder category(String category) {
			this.category = category;
			return this;
		}

		public Builder stock(int stock) {
			this.stock = stock;
			return this;
		}

		public Builder description(String description) {
			this.description = description;
			return this;
		}

		public Builder imagePath(String imagePath) {
			this.imagePath = imagePath;
			return this;
		}
		
		public Builder likeCount(int likeCount) {
			this.likeCount = likeCount;
			return this;
		}

		public ProductDto build() {
			return new ProductDto(num, productName, price, category, stock, description, imagePath, likeCount);
		}
	}

	public static Builder builder() {
		return new Builder();
	}

}
