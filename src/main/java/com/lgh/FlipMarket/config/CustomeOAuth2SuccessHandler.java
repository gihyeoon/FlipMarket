package com.lgh.FlipMarket.config;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomeOAuth2SuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// 파라미터 혹은 세션으로 redirect URL 결정
		String redirectUrl = (String) request.getSession().getAttribute("redirectUrl");
		
		if (redirectUrl == null || redirectUrl.isEmpty()) {
			redirectUrl = "/";
		}
		
		request.getSession().removeAttribute("redirectUrl");
		
		response.sendRedirect(redirectUrl);
	}

}
