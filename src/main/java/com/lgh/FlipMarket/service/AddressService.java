package com.lgh.FlipMarket.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.lgh.FlipMarket.dto.AddressDto;
import com.lgh.FlipMarket.dto.UserDto;
import com.lgh.FlipMarket.entity.Address;
import com.lgh.FlipMarket.entity.User;
import com.lgh.FlipMarket.repository.AddressRepository;
import com.lgh.FlipMarket.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class AddressService {

	private final AddressRepository addressRepository;

	private final UserRepository userRepository;

	public AddressService(AddressRepository addressRepository, UserRepository userRepository) {
		this.addressRepository = addressRepository;
		this.userRepository = userRepository;
	}

	// 로그인한 사용자의 주소지들의 기본 배송지 여부를 false로 수정
	@Transactional
	public int updateDefaultAddress(Long userNum) {
		return addressRepository.updateDefaultAddress(userNum);
	}

	// 기본 주소지 설정
	@Transactional
	public int setDefaultAddress(Long addressNum) {
		return addressRepository.setDefaultAddress(addressNum);
	}

	// 주소 삭제
	@Transactional
	public void deleteAddress(Long addressNum) {
		addressRepository.deleteById(addressNum);
	}
	
	public int countByUserNum(Long userNum) {
		return addressRepository.countByUserNum(userNum);
	}

	// 주소지 등록
	public void addAddress(String zonecode, String roadAddress, String jibunAddress, String detailAddress,
			String buildingName, String addressType, boolean isDefault, UserDto userDto) {
		User user = userRepository.findByEmail(userDto.getEmail())
				.orElseThrow(() -> new IllegalArgumentException("해당하는 사용자는 없습니다."));
		Address address = new Address(user, zonecode, roadAddress, jibunAddress, detailAddress, buildingName,
				addressType, isDefault);
		addressRepository.save(address);
	}

	// 로그인한 사용자가 등록한 주소지 리스트 조회
	public List<AddressDto> findByUserNum(Long userNum) {
		List<Object[]> results = addressRepository.findByUserNum(userNum);

		return results.stream()
				.map(r -> new AddressDto(Long.parseLong(r[0].toString()), r[1].toString(),
						r[2] == null ? "" : r[2].toString(), r[3].toString(), r[4].toString(), r[5].toString(),
						r[6].toString(), Boolean.parseBoolean(r[7].toString())))
				.collect(Collectors.toList());
	}

	// 로그인한 사용자의 주소지들 중 기본 배송지인 것만 조회
	public AddressDto findByUserNumDefault(Long userNum) {
		int cnt = addressRepository.countByUserNumDefault(userNum);
		if (cnt < 1) {
			return AddressDto.builder().roadAddress("").jibunAddress("").build();
		} else {
			Address address = addressRepository.findByUserNumDefault(userNum)
					.orElseThrow(() -> new IllegalArgumentException("주소지 조회 오류"));
			return AddressDto.builder().roadAddress(address.getRoadAddress()).jibunAddress(address.getJibunAddress())
					.build();
		}

	}

}
