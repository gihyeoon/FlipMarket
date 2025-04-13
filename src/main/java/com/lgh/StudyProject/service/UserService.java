package com.lgh.StudyProject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgh.StudyProject.entity.User;
import com.lgh.StudyProject.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public void saveUser(String id, String pwd, String name, int age, String phoneNum) {
		User user = new User(id, pwd, name, age, phoneNum, "USER");
		userRepository.save(user);
	}
	
}
