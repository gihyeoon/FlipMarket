package com.lgh.StudyProject.dto;

public class CartDto {

	private Long num;
	
	public CartDto() {
		// TODO Auto-generated constructor stub
	}
	
	public CartDto(Long num) {
		this.num = num;
	}
	
	public Long getNum() {
		return num;
	}
	
	public static class Builder {
		private Long num;
		
		public Builder num(Long num) {
			this.num = num;
			return this;
		}
		
		public CartDto build() {
			return new CartDto(num);
		}
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
}
