package edu.pnu.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
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
@EnableWebSecurity
public class SecurityConfig {
	@Autowired
	private AuthenticationConfiguration authenticationConfiguration;
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Resource(name="${project.oauth2login.qualifier.name}")
	private AuthenticationSuccessHandler oauth2successHandler;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		// CSRF 보호 비활성화(CsrfFilter 제거)
		http.csrf(csrf->csrf.disable());
		
		// AthorizationFilter 등록
		http.authorizeHttpRequests(auth->auth
				.requestMatchers("/member/**").authenticated()
				.requestMatchers("/manager/**").hasAnyRole("MANAGER", "ADMIN")
				.requestMatchers("/admin/**").hasRole("ADMIN")
				.anyRequest().permitAll());
		
		// Form을 이용한 로그인을 사용하지 않겠다는 명시적 설정
		// UsernamePasswordAutenticationFilter을 상속하는 필터를 만들거기 때문에 명시적 제거
		http.formLogin(frmLogin->frmLogin.disable());
		
		//
		http.httpBasic(basic->basic.disable());
		
		//
		http.sessionManagement(sm->sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		
		// 스프링 시큐리티의
		// 
		http.addFilter(new JWTAuthenticationFilter(authenticationConfiguration.getAuthenticationManager()));
		
		// 스프링 시큐리티가 등록한 필터들 중에서 AuthorizationFilter 앞에 JWTAuthorizationFilter를 삽입한다.
		http.addFilterBefore(new JWTAuthorizationFilter(memberRepository), AuthorizationFilter.class);
		
		// OAuth2
		http.oauth2Login(oauth2->oauth2.successHandler(oauth2successHandler));
		
		return http.build();
		
	}
	
}
