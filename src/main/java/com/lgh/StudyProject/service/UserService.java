package com.lgh.StudyProject.service;

import org.springframework.stereotype.Service;

import com.lgh.StudyProject.dto.UserDto;
import com.lgh.StudyProject.entity.User;
import com.lgh.StudyProject.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService {

	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public UserDto findByNum(Long num) {
		User user = userRepository.findById(num).orElseThrow(() -> new IllegalArgumentException("해당 유저는 존재하지 않습니다."));
		
		return UserDto.builder()
				.num(user.getNum())
				.id(user.getId())
				.name(user.getName())
				.pwd(user.getPwd())
				.age(user.getAge())
				.phoneNum(user.getPhoneNum())
				.build();
	}
	
	public Long findNumById(String id) {
		return userRepository.findById(id).get().getNum();
	}
	
	public void register(UserDto userDto) {
		User user = new User(userDto.getId(), userDto.getPwd(), userDto.getName(), userDto.getAge(),
				userDto.getPhoneNum(), "USER");
		userRepository.save(user);
	}

	@Transactional
	public int updateId(String newId, Long num) {
		return userRepository.updateId(newId, num);
	}

	@Transactional
	public int updatePhoneNum(String newPhoneNum, Long num) {
		return userRepository.updatePhoneNum(newPhoneNum, num);
	}

	@Transactional
	public int updatePwd(String newPwd, Long num) {
		return userRepository.updatePwd(newPwd, num);
	}

	public boolean checkDuplicateId(String id) {
		return userRepository.existsById(id);
	}

	public String selectUserPwdByNum(Long num) {
		return userRepository.selectUserPwdByNum(num);
	}

	public int countUserById(String id) {
		return userRepository.countUserById(id);
	}

}
