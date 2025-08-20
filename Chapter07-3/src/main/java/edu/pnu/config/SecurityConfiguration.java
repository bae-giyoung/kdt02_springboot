package edu.pnu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		// url별 인가 설정
		http.authorizeHttpRequests(auth -> auth
				.requestMatchers("/member/**").authenticated()
				.requestMatchers("/manager/**").hasAnyRole("MANAGER", "ADMIN")
				.requestMatchers("/admin/**").hasRole("ADMIN")
				.anyRequest().permitAll());
		
		// 로그인 url 설정
		http.formLogin(login -> login.loginPage("/login").defaultSuccessUrl("/loginSuccess"));
		
		// 로그아웃 url 설정
		http.logout(logout -> logout.logoutSuccessUrl("/login"));
		
		// 접근 실패 페이지 설정
		http.exceptionHandling(ex -> ex.accessDeniedPage("/accessDenied"));
		
		// csrf 설정
		http.csrf(csrf -> csrf.disable());
		
		return http.build();
	}
	
}
