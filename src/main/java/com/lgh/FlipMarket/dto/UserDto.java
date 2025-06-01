package com.lgh.FlipMarket.dto;

public class UserDto {

	private Long num;

	private String name;

	private String email;

	private String pwd;

	private String provider;

	private String providerId;

	public UserDto() {
	}

	private UserDto(Long num, String name, String email, String pwd, String provider, String providerId) {
		this.num = num;
		this.name = name;
		this.email = email;
		this.pwd = pwd;
		this.provider = provider;
		this.providerId = providerId;
	}

	public Long getNum() {
		return num;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPwd() {
		return pwd;
	}

	public String getProvider() {
		return provider;
	}

	public String getProviderId() {
		return providerId;
	}

	public static class Builder {
		private Long num;
		private String name;
		private String email;
		private String pwd;
		private String provider;
		private String providerId;

		public Builder num(Long num) {
			this.num = num;
			return this;
		}

		public Builder name(String name) {
			this.name = name;
			return this;
		}

		public Builder email(String email) {
			this.email = email;
			return this;
		}

		public Builder pwd(String pwd) {
			this.pwd = pwd;
			return this;
		}

		public Builder provider(String provider) {
			this.provider = provider;
			return this;
		}

		public Builder providerId(String providerId) {
			this.providerId = providerId;
			return this;
		}

		public UserDto build() {
			return new UserDto(num, name, email, pwd, provider, providerId);
		}
	}

	public static Builder builder() {
		return new Builder();
	}

}
