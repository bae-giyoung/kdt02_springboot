package com.rubypaper.board.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf->csrf.disable());
		
		http.authorizeHttpRequests(auth->auth
				.requestMatchers("/board/**").authenticated()
				.requestMatchers("/admin/**").hasRole("ADMIN")
				.anyRequest().permitAll());
		
		http.formLogin(login->login
				.loginPage("/system/login")
				.defaultSuccessUrl("/board/getBoardList", true));
		
		http.exceptionHandling(exc->exc.accessDeniedPage("/system/accessDenied"));
		
		http.logout(logout->logout
				.logoutUrl("/system/logout")
				.invalidateHttpSession(true)
				.logoutSuccessUrl("/"));
		
		return http.build();
	}
}
