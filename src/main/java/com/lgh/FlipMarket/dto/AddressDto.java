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
	
	public static class Builder {
		private Long num;
		private String zonecode;
		private String roadAddress;
		private String jibunAddress;
		private String detailAddress;
		private String buildingName;
		private String addressType;
		private boolean isDefault;

		public Builder num(Long num) {
			this.num = num;
			return this;
		}

		public Builder zonecode(String zonecode) {
			this.zonecode = zonecode;
			return this;
		}

		public Builder roadAddress(String roadAddress) {
			this.roadAddress = roadAddress;
			return this;
		}

		public Builder jibunAddress(String jibunAddress) {
			this.jibunAddress = jibunAddress;
			return this;
		}

		public Builder detailAddress(String detailAddress) {
			this.detailAddress = detailAddress;
			return this;
		}

		public Builder buildingName(String buildingName) {
			this.buildingName = buildingName;
			return this;
		}
		
		public Builder addressType(String addressType) {
			this.addressType = addressType;
			return this;
		}

		public Builder isDefault(boolean isDefault) {
			this.isDefault = isDefault;
			return this;
		}

		public AddressDto build() {
			return new AddressDto(num, zonecode, roadAddress, jibunAddress, detailAddress, buildingName, addressType, isDefault);
		}
	}

	public static Builder builder() {
		return new Builder();
	}

}
