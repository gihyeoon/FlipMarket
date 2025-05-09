package com.lgh.StudyProject.dto;

public class UserDto {

	private Long num;

	private String name;

	private String email;

	private String pwd;

	private int age;

	private String phoneNum;

	public UserDto() {
	}

	private UserDto(Long num, String name, String email, String pwd, int age, String phoneNum) {
		this.num = num;
		this.name = name;
		this.email = email;
		this.pwd = pwd;
		this.age = age;
		this.phoneNum = phoneNum;
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

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getPwd() {
		return pwd;
	}

	public int getAge() {
		return age;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public static class Builder {
		private Long num;
		private String name;
		private String email;
		private String pwd;
		private int age;
		private String phoneNum;

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

		public Builder age(int age) {
			this.age = age;
			return this;
		}

		public Builder phoneNum(String phoneNum) {
			this.phoneNum = phoneNum;
			return this;
		}

		public UserDto build() {
			return new UserDto(num, name, email, pwd, age, phoneNum);
		}
	}

	public static Builder builder() {
		return new Builder();
	}

}
