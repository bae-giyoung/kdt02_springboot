package edu.pnu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import edu.pnu.domain.Member;
import edu.pnu.service.SecurityService;

@Controller
public class SecurityController {
	@Autowired
	private SecurityService securityService;
	
	@GetMapping("/")
	public String home() {
		return "index";
	}
	
	@GetMapping("/member")
	public void member() {
		System.out.println("Member 페이지 접속");
	}
	
	@GetMapping("/manager")
	public void manager() {
		System.out.println("Manager 페이지 접속");
	}
	
	@GetMapping("/admin")
	public void admin() {
		System.out.println("Admin 페이지 접속");
	}
	
	@GetMapping("/join")
	public void join() {}
	
	@PostMapping("/join")
	public String postJoin(Member member) {
		securityService.save(member);
		return "welcome";
	}
}
