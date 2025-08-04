package edu.pnu.controller;

import java.util.ArrayList;
import java.util.Date;
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

@RestController
@RequestMapping("/api")
public class MemberController {
	
	// 데이터 저장용 객체 생성
	private List<MemberDTO> list = new ArrayList<>();
	
	public MemberController() { // 데이터 초기화
		for (int i = 1; i <= 10; i++) {
			list.add(
					MemberDTO.builder()
					.id(i).name("name" + i).pass("pass" + i)
					.regidate(new Date()).build()
			);
		}
	}
	
	@GetMapping("/member")	// 검색 (Read - select All)
	public List<MemberDTO> getAllMember() {
		return list;
	}
	
	@GetMapping("/member/{id}") // 검색 (Read - select One)
	public MemberDTO getMemberById(@PathVariable Integer id) {
		for (MemberDTO member : list) {
			if(member.getId() == id) 
				return member;
		}
		return null;
	}
	
	@PostMapping("/member") // 입력(Create = insert)
	public MemberDTO postMember(@RequestBody MemberDTO memberDTO) {
		MemberDTO member = MemberDTO.builder()
							.id(memberDTO.getId())
							.name(memberDTO.getName())
							.pass(memberDTO.getPass())
							.regidate(memberDTO.getRegidate())
							.build();
		list.add(member);
		return member;
	}
	
	@PutMapping("/member/{id}") // 수정 (Update - 객체 교체)
	public ResponseEntity<?> putMember(@PathVariable Integer id,
								@RequestBody MemberDTO memberDTO) {
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i).getId() == id) {
				list.remove(i);
				list.add(i, memberDTO);
				return ResponseEntity.ok(memberDTO);
			}
		}
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Not Found");
	}
	
	@PatchMapping("/member/{id}") // 수정 (Update - 일부 정보 수정)
//	public MemberDTO patchMember(@PathVariable Integer id,
//									@RequestBody MemberDTO memberDTO) {
	public ResponseEntity<?> patchMember(@PathVariable Integer id,
									@RequestBody MemberDTO memberDTO) {
		
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i).getId() == id) {
				MemberDTO member = list.get(i);
				
				if(memberDTO.getName() != null && !member.getName().equals(memberDTO.getName()))
					member.setName(memberDTO.getName());
				if(memberDTO.getPass() != null && !member.getPass().equals(memberDTO.getPass()))
					member.setPass(memberDTO.getPass());
				if(memberDTO.getRegidate() != null && !member.getRegidate().equals(memberDTO.getRegidate()))
					member.setRegidate(memberDTO.getRegidate());
				
				return ResponseEntity.ok(member);
			}
		}
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Not Found");
	}
	
	@DeleteMapping("/member/{id}") // 삭제 (Delete - delete)
	public void deleteMember(@PathVariable Integer id) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getId() == id)
				list.remove(i); // 인덱스로 remove(i)하지 않고, 객체를 remove 하니 삭제 기능은 잘 됐는데, http응답에 500에러가 떴다. 이유는?
		}
	}
	
}
