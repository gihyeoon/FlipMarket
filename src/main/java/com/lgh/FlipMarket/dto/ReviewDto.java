package com.lgh.FlipMarket.dto;

import java.time.LocalDateTime;

import com.lgh.FlipMarket.entity.Product;
import com.lgh.FlipMarket.entity.User;

public class ReviewDto {

	private Long num;

	private User user;

	private String username;

	private Product product;

	private int rating;

	private String description;

	private LocalDateTime createdAt;

	public ReviewDto() {
		// TODO Auto-generated constructor stub
	}

	public ReviewDto(Long num, User user, Product product, int rating, String description, LocalDateTime createdAt) {
		this.num = num;
		this.user = user;
		this.product = product;
		this.rating = rating;
		this.description = description;
		this.createdAt = createdAt;
	}

	public ReviewDto(Long num, String username, int rating, String description, LocalDateTime createdAt) {
		this.num = num;
		this.username = username;
		this.rating = rating;
		this.description = description;
		this.createdAt = createdAt;
	}

	public Long getNum() {
		return num;
	}

	public User getUser() {
		return user;
	}

	public String getUsername() {
		return username;
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

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public static class Builder {
		private Long num;
		private User user;
		private Product product;
		private int rating;
		private String description;
		private LocalDateTime createdAt;

		public Builder num(Long num) {
			this.num = num;
			return this;
		}

		public Builder user(User user) {
			this.user = user;
			return this;
		}

		public Builder product(Product product) {
			this.product = product;
			return this;
		}

		public Builder rating(int rating) {
			this.rating = rating;
			return this;
		}

		public Builder description(String description) {
			this.description = description;
			return this;
		}

		public Builder createdAt(LocalDateTime createdAt) {
			this.createdAt = createdAt;
			return this;
		}

		public ReviewDto build() {
			return new ReviewDto(num, user, product, rating, description, createdAt);
		}
	}

	public static Builder builder() {
		return new Builder();
	}

}
