package com.lgh.StudyProject.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.lgh.StudyProject.entity.User;

public class CustomUserDetails implements UserDetails {

	private User user;

	public CustomUserDetails(User user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collections = new ArrayList<>();
		collections.add(() -> {
			return user.getRole();
		});
		
		return collections;
	}

	@Override
	public String getPassword() {
		return user.getPwd();
	}

	@Override
	public String getUsername() {
		return user.getId();
	}

	@Override
	public boolean isAccountNonExpired() { // 계정이 만료되었는지
		return true;
	}

	@Override
	public boolean isAccountNonLocked() { // 계정이 잠겼는지
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() { // 비밀번호가 만료되었는지
		return true;
	}

	@Override
	public boolean isEnabled() { // 계정이 사용가능한지
		return true;
	}

}
