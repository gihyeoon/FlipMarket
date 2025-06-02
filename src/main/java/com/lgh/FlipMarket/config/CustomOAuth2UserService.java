package com.lgh.FlipMarket.config;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.lgh.FlipMarket.dto.UserDto;
import com.lgh.FlipMarket.service.UserService;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

	private final UserService userService;

	public CustomOAuth2UserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User user = super.loadUser(userRequest);

		String provider = userRequest.getClientRegistration().getRegistrationId();
		String providerId = user.getAttribute("sub");
		String email = user.getAttribute("email");
		String name = user.getAttribute("name");

		// DB에 해당 사용자가 없을 경우 신규 등록
		int cnt = userService.countUserByEmail(email);
		if (cnt < 1) {
			userService.register(UserDto.builder().email(email).name(name).pwd("").provider(provider)
					.providerId(providerId).build());
		}

		return user;
	}

}
