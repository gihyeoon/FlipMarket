package com.lgh.StudyProject.dto;

public class UserDto {

	private String name;
	
	private String id;
	
	private String pwd;
	
	private int age;
	
	private String phoneNum;
	
	public UserDto() {
	}
	
	public UserDto(String name, String id, String pwd, int age, String phoneNum) {
		this.setName(name);
		this.setId(id);
		this.setPwd(pwd);
		this.setAge(age);
		this.setPhoneNum(phoneNum);
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getPwd() {
		return pwd;
	}


	public void setPwd(String pwd) {
		this.pwd = pwd;
	}


	public int getAge() {
		return age;
	}


	public void setAge(int age) {
		this.age = age;
	}


	public String getPhoneNum() {
		return phoneNum;
	}


	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	

	
}
