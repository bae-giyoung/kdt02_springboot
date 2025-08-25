package edu.pnu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import edu.pnu.filter.JWTAuthenticationFilter;
import edu.pnu.filter.JWTAuthorizationFilter;
import edu.pnu.persistence.MemberRepository;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
	
	private final AuthenticationConfiguration authenticationConfiguration;
	private final MemberRepository memberRepository;
	
	@Resource(name = "${project.oauth2login.qualifier.name}")
	private AuthenticationSuccessHandler oauth2SuccessHandler;
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		//CSRF 보호 비활성화(CsrfFilter 제거)
		http.csrf(csrf->csrf.disable());
		
		// Authorization Filter 등록
		http.authorizeHttpRequests(auth->auth
				.requestMatchers("/member/**").authenticated()
				.requestMatchers("/manager/**").hasAnyRole("MANAGER","ADMIN")
				.requestMatchers("/admin/**").hasRole("ADMIN")
				.anyRequest().permitAll());
		
		// UsernamePasswordAuthenticationFilter가 현재 없지만 명시적 제거
		http.formLogin(frmLogin->frmLogin.disable());
		
		// 
		http.oauth2Login(oauth2->oauth2.successHandler(oauth2SuccessHandler));
		
		
		// Http Basic 인증 방식을 사용하지 않겠다
		// BasicAuthenticationFilter가 현재 없지만 명시적 제거
		http.httpBasic(basic->basic.disable());
		
		// 세션을 유지하지 않겠다고 설정(SessionManagementFilter 등록)
		// Url 호출 되 응답할 때 까지는 유지되지만 응답 후 삭제
		http.sessionManagement(sm->sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		// UsernamePasswordAuthenticationFilter를 상속한 필터이므로 그 superclass 필터가 위치하는 곳에 대신 추가된다.
		http.addFilter(new JWTAuthenticationFilter(authenticationConfiguration.getAuthenticationManager()));
		
		// 스프링 시큐리티가 등록한 필터들 중에서 AuthorizationFilter 앞에 JWTAuthorizationFilter를 삽입한다.
		http.addFilterBefore(new JWTAuthorizationFilter(memberRepository), AuthorizationFilter.class);
		
		return http.build();
	}
}
