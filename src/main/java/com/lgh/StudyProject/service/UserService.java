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

	public void register(UserDto userDto) {
		User user = new User(userDto.getId(), userDto.getPwd(), userDto.getName(), userDto.getAge(),
				userDto.getPhoneNum(), "USER");
		userRepository.save(user);
	}

	@Transactional
	public int updateId(String newId, Long num) {
		return userRepository.updateId(newId, num);
	}

	public boolean checkDuplicateId(String id) {
		return userRepository.existsById(id);
	}

}
