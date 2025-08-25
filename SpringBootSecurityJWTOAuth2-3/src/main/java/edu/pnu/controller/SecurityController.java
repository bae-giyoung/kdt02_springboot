package edu.pnu.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.util.JWTUtil;

@RestController
public class SecurityController {
	
	@GetMapping({"/", "/index"})
	public String index() {
		return "index";
	}
	
	@GetMapping("/member")
	public String member() {
		return "member";
	}
	
	@GetMapping("/manager")
	public String manager() {
		return "manager";
	}
	
	@GetMapping("/admin")
	public String admin() {
		return "admin";
	}
	
	@PostMapping("/api/jwtcallback")
	public ResponseEntity<?> apiCallback(@CookieValue String jwtToken) {
		System.out.println("[SecurityController]jwtToken:" + jwtToken);
		return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, JWTUtil.prefix + jwtToken).build();
	}
}
