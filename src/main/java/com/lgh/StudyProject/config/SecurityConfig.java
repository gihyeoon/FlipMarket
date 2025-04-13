package com.lgh.StudyProject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.lgh.StudyProject.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

	private final CustomUserDetailsService customUserDetailsService;

	public SecurityConfig(CustomUserDetailsService customUserDetailsService, UserDetailsService userDetailsService) {
		this.customUserDetailsService = customUserDetailsService;
		this.userDetailsService = userDetailsService;
	}
	
	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
		AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManagerBuilder.class)
				.userDetailsService(customUserDetailsService)
				.passwordEncoder(passwordEncoder())
				.and()
				.build();
		return authenticationManager;
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		
		provider.setUserDetailsService(customUserDetailsService);
		provider.setPasswordEncoder(passwordEncoder());
		
		return provider;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(
						auth -> auth.requestMatchers("/login", "/register").permitAll().anyRequest().authenticated())
				.formLogin(form -> form.loginPage("/login").loginProcessingUrl("/login")
						.defaultSuccessUrl("/dashboard", true).permitAll().failureHandler((request, response, exception) -> {
							System.out.println("로그인 실패 : " + exception.getMessage());
							response.sendRedirect("/login?error");
						}))
				.logout(logout -> logout.logoutSuccessUrl("/login"));
		
		System.out.println("필터 체인 로그");

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return customUserDetailsService;
	}

}
