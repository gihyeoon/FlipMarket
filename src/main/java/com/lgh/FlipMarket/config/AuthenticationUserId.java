package com.lgh.FlipMarket.config;

import java.util.Map;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.lgh.FlipMarket.service.UserService;

@Configuration
public class AuthenticationUserId {

	private final UserService userService;

	private static final String OAUTH2_EMAIL = "email";

	public AuthenticationUserId(UserService userService) {
		this.userService = userService;
	}

	public Long getUserNum() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = "";
		Long userNum = 0L;

		if (authentication != null && authentication.isAuthenticated()
				&& !(authentication instanceof AnonymousAuthenticationToken)) {
			if (authentication.getPrincipal() instanceof OAuth2User) {
				OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();

				// 네이버 로그인
				if ((String) oauth2User.getAttributes().get(OAUTH2_EMAIL) == null) {
					Map<String, Object> response = (Map<String, Object>) oauth2User.getAttributes().get("response");
					email = (String) response.get(OAUTH2_EMAIL);
				}
				// 구글 로그인
				else {
					email = (String) oauth2User.getAttributes().get(OAUTH2_EMAIL);
				}
			} else {
				email = authentication.getName();
			}

			userNum = userService.findNumByEmail(email);
		} else {
			userNum = 0L;
		}

		return userNum;
	}

}
