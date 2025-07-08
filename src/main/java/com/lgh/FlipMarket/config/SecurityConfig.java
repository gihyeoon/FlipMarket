package com.lgh.FlipMarket.config;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.lgh.FlipMarket.config.oauth2.CustomOAuth2UserService;
import com.lgh.FlipMarket.config.oauth2.CustomOAuth2SuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final CustomUserDetailsService customUserDetailsService;

	private final CustomOAuth2UserService oAuth2UserService;

	private final CustomOAuth2SuccessHandler auth2SuccessHandler;

	private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

	public SecurityConfig(CustomUserDetailsService customUserDetailsService, CustomOAuth2UserService oAuth2UserService,
			CustomOAuth2SuccessHandler auth2SuccessHandler) {
		this.customUserDetailsService = customUserDetailsService;
		this.oAuth2UserService = oAuth2UserService;
		this.auth2SuccessHandler = auth2SuccessHandler;
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	UserDetailsService userDetailsService() {
		return customUserDetailsService;
	}

	@Bean
	AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider(customUserDetailsService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/", "/login", "/findPassword", "/resetPassword", "/register", "/main",
								"/products", "/search/**", "/api/**", "/images/**", "/**.jpg", "/**.png")
						.permitAll().anyRequest().authenticated())
				.formLogin(form -> form.loginPage("/login").loginProcessingUrl("/login")
						.defaultSuccessUrl("/main", true).permitAll().failureHandler((request, response, exception) -> {
							log.info("로그인 실패 : " + exception.getMessage());
							response.sendRedirect("/login?error");
						}))
				.oauth2Login(oauth2 -> {
					oauth2.loginPage("/login");
					oauth2.userInfoEndpoint(userInfo -> userInfo.userService(oAuth2UserService));
					oauth2.successHandler(auth2SuccessHandler);
				}).logout(logout -> {
					logout.logoutSuccessUrl("/main");
					logout.deleteCookies("JSESSIONID", "remember-me"); // 로그아웃 시 쿠기 삭제
				});

		return http.build();
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();

		corsConfiguration.setAllowedOrigins(List.of("http://localhost:8080"));
		corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.setAllowedHeaders(List.of("*"));
		corsConfiguration.setMaxAge(3600L);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfiguration);

		return source;
	}

}
