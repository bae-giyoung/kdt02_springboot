package edu.pnu.filter;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.pnu.domain.Member;
import edu.pnu.util.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// Post/login 요청이 들어오면 이 필터가 실행
@Slf4j
@RequiredArgsConstructor // final이 붙은 필드들을 주입받는 생성자 만들어줌(즉, 인자가 없는 기본 생성자는 없다)
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private final AuthenticationManager authenticationManager; // 인증을 실행하는 객체
	
	// POST/login 요청이 왔을 때 인증을 시도하는 메소드
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
		// request에서 json 타입의 [username/password]를 읽어서 Member 객체를 생성한다.
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			// request에서 json 타입의 [username/password]를 읽어서 Member 객체를 생성한다.
			Member member = mapper.readValue(request.getInputStream(), Member.class);
			
			// Security에게 자격 증명 요청에 필요한 객체 생성
			Authentication authToken = new UsernamePasswordAuthenticationToken(member.getUsername(), member.getPassword());
			
			// 인증 진행 -> UserDetailService
			//
			return authenticationManager.authenticate(authToken);
		} catch(Exception e) {
			//Log.info(e.getMessage()); // 예외 발생 로그 출력
			response.setStatus(HttpStatus.UNAUTHORIZED.value()); // 자격 증명에 실패하면 응답코드 리턴
		}
		return null;
	}
	
	// 인증이 성공했을 때 실행되는 후처리 메소드
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
		// 자격 증명이 성공하면 loadUserByUsername에서 만든 객체가 authResult에 담겨져 있다.
		User user = (User)authResult.getPrincipal();
		System.out.println("auth:" + user);
		
		// username으로 JWT를 생성한다.
		String token = JWTUtil.getJWT(user.getUsername());
		
		// Response Header[Authorization]에 JWT를 저장해서 응답한다.
		response.addHeader(HttpHeaders.AUTHORIZATION, token);
		response.setStatus(HttpStatus.OK.value());
	}
}
