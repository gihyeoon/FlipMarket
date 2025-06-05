package com.lgh.FlipMarket.service;

import org.springframework.stereotype.Service;

import com.lgh.FlipMarket.dto.UserDto;
import com.lgh.FlipMarket.entity.User;
import com.lgh.FlipMarket.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {

	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	// 마이페이지에서 로그인한 사용자 조회 시 사용
	public UserDto findByNum(Long num) {
		User user = userRepository.findById(num).orElseThrow(() -> new IllegalArgumentException("해당 유저는 존재하지 않습니다."));

		return UserDto.builder().num(user.getNum()).email(user.getEmail()).name(user.getName()).pwd(user.getPwd())
				.provider(user.getProvider()).createdAt(user.getCreatedAt()).build();
	}

	// 아이디(이메일)을 통해 사용자 번호 조회
	public Long findNumByEmail(String email) {
		return userRepository.findByEmail(email).get().getNum();
	}

	// 회원가입
	public void register(UserDto userDto) {
		User user = new User(userDto.getEmail(), userDto.getPwd(), userDto.getName(), userDto.getProvider(),
				userDto.getProviderId(), "USER");
		userRepository.save(user);
	}

	// 회원정보 수정 페이지에서의 이메일 수정
	public int updateEmail(String newEmail, Long num) {
		return userRepository.updateEmail(newEmail, num);
	}

	// 회원정보 수정 페이지에서의 연락처 수정
	public int updatePhoneNum(String newPhoneNum, Long num) {
		return userRepository.updatePhoneNum(newPhoneNum, num);
	}

	// 회원정보 수정 페이지에서의 비밀번호 수정
	public int updatePwd(String newPwd, Long num) {
		return userRepository.updatePwd(newPwd, num);
	}

	// 회원가입 및 회원정보 수정 시 이미 존재하는 이메일 정보가 있는지 확인
	@Deprecated
	public boolean checkDuplicateEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	// 사용자 번호를 통해 비밀번호 조회 (마이페이지에서 현재 비밀번호 비교를 하기 위함)
	public String selectUserPwdByNum(Long num) {
		return userRepository.selectUserPwdByNum(num);
	}

	// 회원가입 및 회원정보 수정 시 이미 존재하는 이메일 정보가 있는지 확인
	public int countUserByEmail(String email) {
		return userRepository.countUserByEmail(email);
	}

	// 회원탈퇴
	@Transactional
	public void deleteByNum(Long userNum) {
		userRepository.deleteById(userNum);
	}

}
