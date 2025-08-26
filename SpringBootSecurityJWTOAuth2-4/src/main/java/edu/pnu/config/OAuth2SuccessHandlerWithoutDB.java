package edu.pnu.config;

import java.io.IOException;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import edu.pnu.util.JWTUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component("OAuth2SuccessHandlerWithoutDB")
public class OAuth2SuccessHandlerWithoutDB extends OAuth2SuccessHandler {
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		Map<String, String> map = getUserInfo(authentication);
		
		// JWT 생성
		String token = JWTUtil.getJWT(map.get("provider"), map.get("email"));
		
		sendJWTtoClient(response, token);
	}
}
