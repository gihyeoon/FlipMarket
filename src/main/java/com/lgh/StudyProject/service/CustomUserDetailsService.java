package com.lgh.StudyProject.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lgh.StudyProject.entity.User;
import com.lgh.StudyProject.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private final UserRepository userRepository;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

	public CustomUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("로그인 시도");
		log.info(username);
		User user = userRepository.findById(username)
				.orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다.")); // orElseThrow 메서드는 중괄호를 이용하여 범위를 정해주지 못한다.
		return org.springframework.security.core.userdetails.User.builder()
				.username(user.getId())
				.password(user.getPwd())
				.roles(user.getRole())
				.build();
	}

}
