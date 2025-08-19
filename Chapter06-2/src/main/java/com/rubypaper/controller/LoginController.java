package com.rubypaper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.rubypaper.domain.Member;
import com.rubypaper.sevice.MemberService;

@SessionAttributes("member")
@Controller
public class LoginController {
	@Autowired
	private MemberService memberService;
	
	@GetMapping("/login")
	public void loginView() {
		
	}
	
	@PostMapping("/login")
	public String login(Member member, Model model) {
		Member findMember = memberService.getMember(member);
		
		// 로그인에 성공하면
		if(findMember != null && findMember.getPassword().equals(member.getPassword())) {
			model.addAttribute("member", findMember); // 세션에 "member"이름의 속성이 존재하므로 findMember는 자동으로 세션에 저장
			return "redirect:getBoardList";// 로그인에 성공한 뒤 "/getBoardList"로 redirect
		}
		return "redirect:login";
	}
	
	@GetMapping("/logout")
	public String logout(SessionStatus status) {
		status.setComplete(); // 세션에 저장되어 있는 속성들 중에서 @SessionAttrubute로 지정된 속성들을 삭제
		return "redirect:index.html";
	}
}
