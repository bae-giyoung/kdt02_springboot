package edu.pnu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
	@GetMapping("/login")
	public void login() {
		System.out.println("로그인 페이지 접속");
	}
	
	@GetMapping("/loginSuccess")
	public void loginSuccess() {
		System.out.println("로그인 성공 페이지 접속");
	}
	
	@GetMapping("/accessDenied")
	public void accessDenied() {
		System.out.println("접속 권한 없음 페이지 접속");
	}
}
