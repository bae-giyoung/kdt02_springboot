package edu.pnu.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import edu.pnu.domain.Member;
import edu.pnu.domain.Role;
import edu.pnu.persistence.MemberRepository;
import edu.pnu.util.CustomMyUtil;
import edu.pnu.util.JWTUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	@Autowired private MemberRepository memRepo;
	@Autowired private PasswordEncoder encoder;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) 
			throws IOException, ServletException {
		
		OAuth2User user = (OAuth2User)authentication.getPrincipal();
		// 임의의 사용자 아이디를 생성
		String username = CustomMyUtil.getUsernameFromOAuth2User(user);
		if(username == null) {
			throw new ServletException("Cannot generate username from oauth2user!");
		}
		
		// OAuth2 로그인 사용자에 대해 JWT를 이용한 검증 목적으로 저장. 비밀번호는 실제 사용되지 않으므로 더미로 설정. 설정하지 않으면 오류 발생 하므로.
		memRepo.save(Member.builder().username(username).password(encoder.encode("a1b2c3d4"))
				.role(Role.ROLE_MEMBER).enabled(true).build());
		String jwtToken = JWTUtil.getJWT(username);
		response.setHeader(HttpHeaders.AUTHORIZATION, jwtToken); // 현재 테스트에서만 response를 볼 수 있다
	}
}
