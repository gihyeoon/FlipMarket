package com.lgh.StudyProject.dto;

public class ProductDto {

	private Long num;

	private String productName;

	private int price;

	private String category;

	private String description;

	private String imagePath;

	public ProductDto() {
		// TODO Auto-generated constructor stub
	}

	private ProductDto(Long num, String productName, int price, String category, String description, String imagePath) {
		this.num = num;
		this.productName = productName;
		this.price = price;
		this.category = category;
		this.description = description;
		this.imagePath = imagePath;
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

	public static class Builder {
		private Long num;
		private String productName;
		private int price;
		private String category;
		private String description;
		private String imagePath;

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

		public Builder description(String description) {
			this.description = description;
			return this;
		}

		public Builder imagePath(String imagePath) {
			this.imagePath = imagePath;
			return this;
		}

		public ProductDto build() {
			return new ProductDto(num, productName, price, category, description, imagePath);
		}
	}

	public static Builder builder() {
		return new Builder();
	}

}
