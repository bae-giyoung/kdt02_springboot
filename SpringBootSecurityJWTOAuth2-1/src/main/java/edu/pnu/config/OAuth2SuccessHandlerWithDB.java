package edu.pnu.config;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import edu.pnu.domain.Member;
import edu.pnu.domain.Role;
import edu.pnu.persistence.MemberRepository;
import edu.pnu.util.JWTUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component("OAuth2SuccessHandlerWithDB")
public class OAuth2SuccessHandlerWithDB extends SimpleUrlAuthenticationSuccessHandler {
	@Autowired private MemberRepository memRepo;
	@Autowired private PasswordEncoder encoder;
	
	@SuppressWarnings("unchecked")
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, 
			Authentication authentication) throws IOException, ServletException {
		
		OAuth2AuthenticationToken oAuth2Token = (OAuth2AuthenticationToken)authentication;
		String provider = oAuth2Token.getAuthorizedClientRegistrationId(); //Provider 확인
		System.out.println("로그인 Provider: " + provider);
		
		OAuth2User user = (OAuth2User)oAuth2Token.getPrincipal();
		String email = null;
		Map<String, Object> resp = null;
		
		if(provider.equals("naver")) {			
			resp = (Map<String, Object>)user.getAttributes().get("response");
			email = (String)resp.get("email");
			
		} else if(provider.equals("kakao")) {			
			resp = (Map<String, Object>)user.getAttributes().get("kakao_account"); // 반드시 스코프 설정해줘야함!! //System.out.println("resp: " + resp);
			email = (String)resp.get("email");
			
		} else {
			email = (String)user.getAttributes().get("email"); // 사용자 정보(email) 확인			
		}
		System.out.println("사용자 이메일: " + email);
		//System.out.println("사용자 user: " + user);
		
		String username = provider + "_" + email;
		memRepo.save(Member.builder().username(username).password(encoder.encode("1a2b3c4f")).role(Role.ROLE_MEMBER).enabled(true).build());
		String jwtToken = JWTUtil.getJWT(username);
		System.out.println("jwtToken:[" + jwtToken + "]");
		
		response.addHeader(HttpHeaders.AUTHORIZATION, jwtToken);
	}
}
