package edu.pnu.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
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

@RestController
@RequestMapping("/api")
public class MemberController {
	private MemberService memberService = new MemberService();
	
	@GetMapping("/member")	// 검색 - all
	public List<MemberDTO> getAllMember() {
		return memberService.getAllMember();
	}
	
	@GetMapping("/member/{id}")	// 검색 - one
	public MemberDTO getMemberById(@PathVariable Integer id) {
		return memberService.getMemberById(id);
	}
	
	@PostMapping("/member")	// 입력 (Create - Insert)
	public ResponseEntity<?> postMember(@RequestBody MemberDTO memberDTO) {
		
		MemberDTO member = memberService.postMember(memberDTO);
		if(member == null)
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("데이터 입력 실패.");
		else
			return ResponseEntity.ok(member);
	}
	
	@PutMapping("/member/{id}")	// 수정 (Update - 객체 교체)
	public ResponseEntity<?> putMember(@PathVariable Integer id,
								@RequestBody MemberDTO memberDTO) {
		
		MemberDTO member = memberService.putMember(id, memberDTO);
		if(member == null)
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("데이터 수정(교체) 실패");
		else
			return ResponseEntity.ok(member);
	}
	
	@PatchMapping("/member/{id}")	// 수정 (Update - 일부 정보 수정)
	public ResponseEntity<?> patchMember(@PathVariable Integer id,
								@RequestBody MemberDTO memberDTO) {
		
		MemberDTO member = memberService.patchMember(id, memberDTO);
		if(member == null)
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("데이터 수정(일부 수정) 실패");
		else
			return ResponseEntity.ok(member);
	}
	
	@DeleteMapping("/member/{id}")	// 삭제 (Delete - delete)
	public ResponseEntity<?> deleteMember(@PathVariable Integer id) {
		int cnt = memberService.deleteMember(id);
		
		if(cnt == 0)
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("데이터 삭제 실패");
		else
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("데이터 삭제 성공");
	}
}
