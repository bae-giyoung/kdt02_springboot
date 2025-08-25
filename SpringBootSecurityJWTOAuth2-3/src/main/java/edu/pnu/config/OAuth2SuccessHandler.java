package edu.pnu.config;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import edu.pnu.util.JWTUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	
	@SuppressWarnings("unchecked")
	Map<String, String> getUserInfo(Authentication authentication) {
		OAuth2AuthenticationToken oAuth2Token = (OAuth2AuthenticationToken)authentication;
		String provider = oAuth2Token.getAuthorizedClientRegistrationId();
		System.out.println("[OAuth2SuccessHandler]Provider: " + provider);
		
		OAuth2User user = (OAuth2User)oAuth2Token.getPrincipal();
		String email = null;
		if(provider.equalsIgnoreCase("naver")) {
			email = (String)((Map<String, Object>)user.getAttribute("response")).get("email");
		} else {
			// google, facebook, github
			email = (String)user.getAttribute("email");
		}
		System.out.println("[OAuth2SuccessHandler]email: " + email);
		return Map.of("provider", provider, "email", email);
	}
	
	void sendJWTtoClient(HttpServletResponse response, String token) throws IOException {
		System.out.println("[OAuth2SuccessHandler]token: " + token);
		response.addHeader(HttpHeaders.AUTHORIZATION, token);
		
		// Cookie에 jwt 추가
		Cookie cookie = new Cookie("jwtToken", token.replaceAll(JWTUtil.prefix, ""));
		cookie.setHttpOnly(true); // JS에서 접근 못하게
		cookie.setSecure(false); // HTTPS에서만 동작
		cookie.setPath("/");
		cookie.setMaxAge(10); // 1초
		response.addCookie(cookie);
		
		// callback.html로 리다이렉트 ==> 프론트가 있다면 Front의 callback을 호출
		response.sendRedirect("/callback.html");
	}

}
