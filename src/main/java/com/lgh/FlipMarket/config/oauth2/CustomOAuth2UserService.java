package com.lgh.FlipMarket.config.oauth2;

import java.util.Map;

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
		String providerId = "";

		Map<String, Object> attributes = user.getAttributes();

		OAuth2UserInfo userInfo;

		if ("google".equals(provider)) {
			userInfo = new GoogleOAuth2UserInfo(attributes);
			providerId = user.getAttribute("sub");
		} else if ("naver".equals(provider)) {
			userInfo = new NaverOAuth2UserInfo(attributes);
			Map<String, Object> response = (Map<String, Object>) user.getAttributes().get("response");
			providerId = (String) response.get("id");
		} else {
			throw new OAuth2AuthenticationException("지원하지 않는 로그인 서비스입니다." + provider);
		}

		String email = userInfo.getEmail();
		String name = userInfo.getName();

		// DB에 해당 사용자가 없을 경우 신규 등록
		int cnt = userService.countUserByEmail(email);
		if (cnt < 1) {
			userService.register(UserDto.builder().email(email).name(name).pwd("").provider(provider)
					.providerId(providerId).build());
		}

		return user;
	}

}
