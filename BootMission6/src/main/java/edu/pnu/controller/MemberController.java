package edu.pnu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.Member;
import edu.pnu.service.MemberService;

@RestController
@RequestMapping("/api")
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	@GetMapping("/member")
	public List<Member> getMembers() {
		return memberService.getMembers();
	}
	
	@GetMapping("/member/{id}")
	public Member getMember(@PathVariable Integer id) {
		return memberService.getMember(id);
	}
	
	// 삽입
	@PostMapping("/member")
	public Member postMember(@RequestBody Member member) {
		return memberService.postMember(member);
	}
	
	// 수정 - 객체 교체
	@PutMapping("/member/{id}")
	public Member putMember(@PathVariable Integer id, @RequestBody Member member) {
		return memberService.putMember(id, member);
	}
	
	// 수정 - 일부 수정
	@PatchMapping("/member/{id}")
	public Member pathMember(@PathVariable Integer id, @RequestBody Member member) {
		return memberService.patchMember(id, member);
	}
	
	// 삭제
	@DeleteMapping("/member/{id}")
	public Member deleteMember(@PathVariable Integer id) {
		return memberService.deleteMember(id);
	}
	
}