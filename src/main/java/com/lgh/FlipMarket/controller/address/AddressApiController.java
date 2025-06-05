package com.lgh.FlipMarket.controller.address;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lgh.FlipMarket.dto.UserDto;
import com.lgh.FlipMarket.service.AddressService;
import com.lgh.FlipMarket.service.UserService;

@RestController
@RequestMapping("/api")
public class AddressApiController {

	private final AddressService addressService;

	private final UserService userService;

	private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

	public AddressApiController(AddressService addressService, UserService userService) {
		this.addressService = addressService;
		this.userService = userService;
	}

	@PostMapping("/deleteAddress")
	public ResponseEntity<?> deleteAddress(@RequestBody Map<String, String> data) {
		Long addressNum = Long.parseLong(data.get("addressNum"));
		addressService.deleteAddress(addressNum);
		return ResponseEntity.ok("주소 삭제 완료");
	}

	@PostMapping("/setDefaultAddress")
	public ResponseEntity<?> setDefaultAddress(@RequestBody Map<String, String> data) {
		Long userNum = Long.parseLong(data.get("userNum"));
		Long addressNum = Long.parseLong(data.get("addressNum"));

		// 먼저 기본 배송지로 설정되었던 주소들을 false로 바꾼다.
		addressService.updateDefaultAddress(userNum);

		// 선택한 주소를 기본 배송지로 설정한다.
		addressService.setDefaultAddress(addressNum);

		return ResponseEntity.ok("기본 주소 설정 완료");
	}

	@PostMapping("/addAddress")
	public ResponseEntity<?> addAddress(@RequestBody Map<String, String> data) {
		Long userNum = Long.parseLong(data.get("userNum"));
		boolean isDefault = Boolean.parseBoolean(data.get("isDefault"));
		UserDto userDto = userService.findByNum(userNum);

		// 만약 사용자가 이전에 등록한 주소들이 없다면 지금 등록할 주소지를 기본 배송지로 설정한다.
		if (addressService.countByUserNum(userNum) < 1) {
			isDefault = true;
		}

		// 만약 추가한 주소지를 기본 배송지로 설정했다면 기존의 기본 배송지를 false로 설정한다.
		if (isDefault) {
			addressService.updateDefaultAddress(userNum);
		}

		log.info("기본 주소 선택 여부: " + isDefault);

		addressService.addAddress(data.get("zonecode"), data.get("roadAddress"), data.get("jibunAddress"),
				data.get("detailAddress"), data.get("buildingName"), data.get("addressType"), isDefault, userDto);
		return ResponseEntity.ok("주소 등록 완료");
	}

}
