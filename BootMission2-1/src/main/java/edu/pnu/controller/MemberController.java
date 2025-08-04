package edu.pnu.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.MemberDTO;
import edu.pnu.service.MemberService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor // Lombok Annotation을 이용한 방법
public class MemberController {
	private final MemberService memberService;
	
	@GetMapping("/member")	// 검색 - all
	public List<MemberDTO> getAllMember() {
		return memberService.getAllMember();
	}
	
	@GetMapping("/member/{id}")	// 검색 - one
	public MemberDTO getMemberById(@PathVariable Integer id) {
		return memberService.getMemberById(id);
	}
	
	@PostMapping("/member")	// 입력 (Create - Insert)
	public MemberDTO postMember(@RequestBody MemberDTO memberDTO) {
		return memberService.postMember(memberDTO);
	}
	
	@PutMapping("/member/{id}")	// 수정 (Update - 객체 교체)
	public ResponseEntity<?> putMember(@PathVariable Integer id,
								@RequestBody MemberDTO memberDTO) {
		return memberService.putMember(id, memberDTO);
	}
	
	@PatchMapping("/member/{id}")	// 수정 (Update - 일부 정보 수정)
	public ResponseEntity<?> patchMember(@PathVariable Integer id,
								@RequestBody MemberDTO memberDTO) {
		return memberService.patchMember(id, memberDTO);
	}
	
	@DeleteMapping("/member/{id}")	// 삭제 (Delete - delete)
	public void deleteMember(@PathVariable Integer id) {
		memberService.deleteMember(id);
	}
}
