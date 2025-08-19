package com.rubypaper.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // 이 클래스의 인스턴스를 ?? Container에 저장 > IOC 컨테이너와 Spring 컨테이너 개념 정리!
public class SecurityConfig {
	
	
	// @Bean은 @Configuration과 같이 쓰이는데 "@Bean이 적용된 메서드가 반환하는 객체를 ?? Container에 저장"
	// "SecurityFilterChain"을 등록하면 더 이상 로그인을 강제하지 않는다!
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		
		// 사용자 인증과 권한을 설정
		http.authorizeHttpRequests(security -> security
				.requestMatchers("/member/**").authenticated()
				.requestMatchers("/manager/**").hasAnyRole("MANAGER", "ADMIN")
				.requestMatchers("/admin/**").hasRole("ADMIN")
				.anyRequest().permitAll()); // anyRequest() 순서 매우 중요!!
		
		// 로그인 페이지 설정
		http.formLogin(form -> form
								.loginPage("/login")
								.defaultSuccessUrl("/loginSuccess", true)); // 무조건 성공하면 이 url로 이동
		
		// 로그아웃 설정
		http.logout(logout -> logout
				// 현재 브라우저와 연결된 세션 강제 종료(삭제)
				.invalidateHttpSession(true)
				// 세션 아이디가 저장된 쿠키 삭제
				.deleteCookies("JSESSIONID")
				// 로그아웃 후 이동할 URL 지정
				.logoutSuccessUrl("/login"));
		
		// 접근 권합 없음 페이지 설정
		http.exceptionHandling(ex -> ex.accessDeniedPage("/accessDenied"));
		
		// 크로스 사이트 위조 요청에 대한 설정 (Cross-Site Request Forgery)
		// RESTfull을 사용하기 위해서는 csrf 기능을 비활성화 해야 한다.
		http.csrf(cf -> cf.disable());
		
		return http.build();
	}
	
	// JDBC 방식
//	@Autowired
//	private DataSource dataSource;
	
//	@Autowired
//	public void authenticate(AuthenticationManagerBuilder auth) throws Exception {
		// 임시 테스트용 사용자 계정을 메모리에 등록해서 권한 관계가 제대로 작동하는지 확인
//		auth.inMemoryAuthentication().withUser("member").password("{noop}abcd").roles("MEMBER"); // No Operation 비밀번호가 암호화 되어 있지 않다
//		auth.inMemoryAuthentication().withUser("manager").password("{noop}abcd").roles("MANAGER");
//		auth.inMemoryAuthentication().withUser("admin").password("{noop}abcd").roles("ADMIN");
		
		// JDBC 방식
//		String query1 = "select id username, concat('{noop}', password) password, true enabled from member where id=?";
//		String query2 = "select id, role from member where id=?";
//		auth.jdbcAuthentication()
//			.dataSource(dataSource)
//			.usersByUsernameQuery(query1)
//			.authoritiesByUsernameQuery(query2);
//	}
	
	
	// 비밀번호 암호화 Bean 객체 등록
	@Bean
	PasswordEncoder passwordEncoder() { // 비가역 그러므로 비밀번호 비교할 때는 decode가 아니라 내가 입력한 비밀번호를 encoding한 값과 db에 저장된 암호화된 비밀번호 값을 비교하는것!
		return new BCryptPasswordEncoder();
	}
}
