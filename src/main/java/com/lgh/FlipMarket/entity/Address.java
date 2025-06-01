package com.lgh.FlipMarket.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "address")
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long num;

	@ManyToOne
	@JoinColumn(name = "user_num", referencedColumnName = "num")
	private User user;

	private String zonecode;

	private String roadAddress;

	private String jibunAddress;

	private String detailAddress;

	private String buildingName;

	@Column(length = 1)
	private String addressType;

	private boolean isDefault;

	public Address() {
		// TODO Auto-generated constructor stub
	}

	public Address(User user, String zonecode, String roadAddress, String jibunAddress, String detailAddress,
			String buildingName, String addressType, boolean isDefault) {
		this.user = user;
		this.zonecode = zonecode;
		this.roadAddress = roadAddress;
		this.jibunAddress = jibunAddress;
		this.detailAddress = detailAddress;
		this.buildingName = buildingName;
		this.addressType = addressType;
		this.isDefault = isDefault;
	}
	
	public User getUser() {
		return user;
	}
	
	public String getZonecode() {
		return zonecode;
	}
	
	public String getRoadAddress() {
		return roadAddress;
	}
	
	public String getJibunAddress() {
		return jibunAddress;
	}
	
	public String getDetailAddress() {
		return detailAddress;
	}
	
	public String getBuildingName() {
		return buildingName;
	}
	
	public String getAddressType() {
		return addressType;
	}
	
	public boolean isDefault() {
		return isDefault;
	}

}
