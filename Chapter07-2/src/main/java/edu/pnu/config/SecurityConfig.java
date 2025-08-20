package edu.pnu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// Spring Security 설정 클래스
@Configuration
public class SecurityConfig {
	
	// default PasswordEncoder가 없으면 뜨는 에러 메시지
	// Given that there is no default password encoder configured, each password must have a password encoding prefix. Please either prefix this password with '{noop}' or set a default password encoder in `DelegatingPasswordEncoder`.
	// 반드시 인코더를 설정해줘야 한다! // {noop}으로 비교하는 것은 안좋음
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	SecurityFilterChain registerSecurityFilterChain(HttpSecurity http) throws Exception {
		
		// Restfull API를 사용하므로 csrf 꺼준다.
		http.csrf(csrf -> csrf.disable());
		
		// url에 대한 인가 설정
		http.authorizeHttpRequests(auth -> auth
				.requestMatchers("/member/**").authenticated()
				.requestMatchers("/manager/**").hasAnyRole("MANAGER", "ADMIN")
				.requestMatchers("/admin/**").hasRole("ADMIN")
				.anyRequest().permitAll());
		
		// 로그인 설정
		http.formLogin(login -> login.loginPage("/login").defaultSuccessUrl("/loginSuccess"));
		
		// 로그아웃 설정
		http.logout(logout -> logout.logoutSuccessUrl("/login"));
		
		// exception 설정
		http.exceptionHandling(exception -> exception.accessDeniedPage("/accessDenied"));
		
		return http.build();
	}
}
