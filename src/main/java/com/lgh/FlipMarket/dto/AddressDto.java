package com.lgh.FlipMarket.dto;

public class AddressDto {

	private Long num;

	private String zonecode;

	private String roadAddress;

	private String jibunAddress;

	private String detailAddress;

	private String buildingName;

	private String addressType;

	private boolean isDefault;

	public AddressDto() {
		// TODO Auto-generated constructor stub
	}

	public AddressDto(Long num, String zonecode, String roadAddress, String jibunAddress, String detailAddress,
			String buildingName, String addressType, boolean isDefault) {
		this.num = num;
		this.zonecode = zonecode;
		this.roadAddress = roadAddress;
		this.jibunAddress = jibunAddress;
		this.detailAddress = detailAddress;
		this.buildingName = buildingName;
		this.addressType = addressType;
		this.isDefault = isDefault;
	}

	public Long getNum() {
		return num;
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
