package edu.pnu.config;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import edu.pnu.util.JWTUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component("OAuth2SuccessHandlerWithoutDB")
public class OAuth2SuccessHandlerWithoutDB extends SimpleUrlAuthenticationSuccessHandler {
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
			email = (String)resp.get("email"); // 사용자 정보(email) 확인
			
		} else if(provider.equals("kakao")) {			
			resp = (Map<String, Object>)user.getAttributes().get("kakao_account");
			email = (String)resp.get("email");
			
		} else {
			email = (String)user.getAttributes().get("email");			
		}
		System.out.println("사용자 이메일: " + email);
		//System.out.println("사용자 user: " + user);
		
		String jwtToken = JWTUtil.getJWT(provider, email);
		System.out.println("jwtToken:[" + jwtToken + "]");
		
		response.addHeader(HttpHeaders.AUTHORIZATION, jwtToken);
	}
}
