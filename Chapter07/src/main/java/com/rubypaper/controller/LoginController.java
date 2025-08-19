package com.rubypaper.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rubypaper.config.SecurityUser;

@Controller
public class LoginController {
	@GetMapping("/login")
	public void login() {
		System.out.println("login 요청");
	}
	
	@GetMapping("/loginSuccess")
	public void loginSuccess() {
		System.out.println("loginSuccess 요청");
	}
	
	@GetMapping("/accessDenied")
	public void accessDenied() {
		System.out.println("accessDenied");
	}
	
	// 로그인 세션 정보 확인용 URL
	@GetMapping("/auth1")
	public @ResponseBody ResponseEntity<?> auth1(@AuthenticationPrincipal SecurityUser user) {
		if(user == null) {
			return ResponseEntity.ok("로그인 상태가 아닙니다.");
		}
		return ResponseEntity.ok(user);
	}
	
	// 로그인 세션 전체 정보 확인용 URL
	@GetMapping("/auth")
	public @ResponseBody void auth2(Authentication auth) {
		System.out.println(auth.getPrincipal());
		System.out.println(auth.getCredentials());
		System.out.println(auth.getAuthorities());
	}
}
