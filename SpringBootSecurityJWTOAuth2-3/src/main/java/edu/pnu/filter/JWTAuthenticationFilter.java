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

@RequiredArgsConstructor
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private final AuthenticationManager authenticationManager; // 인증을 실행하는 객체
	
	// POST/login 요청이 왔을 때 인증을 시도하는 메서드
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws org.springframework.security.core.AuthenticationException {
		// JSON/Object Mapping 객체 생성
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			// request에서 json 타입의 [username/password]를 읽어서 Member 객체를 생성
			Member member = mapper.readValue(request.getInputStream(), Member.class);
			
			// Security에게 자격 증명 요청에 필요한 객체 생성
			Authentication authToken = new UsernamePasswordAuthenticationToken(member.getUsername(), member.getPassword());
			
			// 인증 진행 -> UserDetailService의 loadUserByUsername에서 DB로부터 사용자 정보를 읽어온 뒤
			// 사용자 입력 정보와 비교한 뒤 자격 증명에 성공하면 Authentication 객체를 만들어 리턴
			return authenticationManager.authenticate(authToken);
		} catch (Exception e) {
			System.out.println("[JWTAuthenticationFilter]" + e.getMessage());
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
		}
		
		return null;
	}
	
	// 인증이 성공했을 때 실행되는 후처리 메서드
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		User user = (User)authResult.getPrincipal();
		
		System.out.println("[JWTAuthenticationFilter]auth: " + user);
		
		String token = JWTUtil.getJWT(user.getUsername());
		
		response.addHeader(HttpHeaders.AUTHORIZATION, token);
		response.setStatus(HttpStatus.OK.value());
	}
}
