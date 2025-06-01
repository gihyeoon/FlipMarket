package com.lgh.FlipMarket.service;

import org.springframework.stereotype.Service;

import com.lgh.FlipMarket.dto.UserDto;
import com.lgh.FlipMarket.entity.Address;
import com.lgh.FlipMarket.entity.User;
import com.lgh.FlipMarket.repository.AddressRepository;
import com.lgh.FlipMarket.repository.UserRepository;

@Service
public class AddressService {

	private final AddressRepository addressRepository;

	private final UserRepository userRepository;

	public AddressService(AddressRepository addressRepository, UserRepository userRepository) {
		this.addressRepository = addressRepository;
		this.userRepository = userRepository;
	}

	public void addAddress(String zonecode, String roadAddress, String jibunAddress, String detailAddress,
			String buildingName, String addressType, boolean isDefault, UserDto userDto) {
		User user = userRepository.findByEmail(userDto.getEmail())
				.orElseThrow(() -> new IllegalArgumentException("해당하는 사용자는 없습니다."));
		Address address = new Address(user, zonecode, roadAddress, jibunAddress, detailAddress, buildingName,
				addressType, isDefault);
		addressRepository.save(address);
	}

}
