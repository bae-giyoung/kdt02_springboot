package com.rubypaper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityController {
	@GetMapping("/")
	public String index() {
		System.out.println("index 요청");
		return "index"; // index.html을 찾는다
	}
	
	@GetMapping("/member")
	public void member() { // void니까 매핑 url 이름의 member.html을 찾는다
		System.out.println("Member 요청");
	}
	
	@GetMapping("/manager")
	public void manager() {
		System.out.println("Manager 요청");
	}
	
	@GetMapping("/admin")
	public void admin() {
		System.out.println("Admin 요청");
	}
}
