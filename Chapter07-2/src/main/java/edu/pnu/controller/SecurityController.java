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
	SecurityService securityService;
	
	@GetMapping("/")
	public String home() {
		return "index";
	}
	
	@GetMapping("/member")
	public void member() {}
	
	@GetMapping("/manager")
	public void manager() {}
	
	@GetMapping("/admin")
	public void admin() {}
	
	@GetMapping("/join")
	public void join() {}
	
	@PostMapping("/join")
	public String postJoin(Member member) {
		securityService.save(member);
		return "redirect:welcome";
	}
	
	@GetMapping("/welcome")
	public void welcome() {}
}
