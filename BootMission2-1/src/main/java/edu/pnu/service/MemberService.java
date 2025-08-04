package edu.pnu.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import edu.pnu.domain.MemberDTO;

@Service
public class MemberService {
	private List<MemberDTO> list = new ArrayList<>();
	
	public MemberService() { // 데이터 초기화
		for(int i=1; i<=10; i++) {
			list.add(MemberDTO.builder()
					.id(i).name("name" + i).pass("pass" + i)
					.regidate(new Date()).build());
		}
	}
	
	// 검색 (Read - select All)
	public List<MemberDTO> getAllMember() { // 컨트롤러와 같은 메서드명을 사용하면 유지 보수에 좋다!!!!!
		return list;
	}
	
	// 검색 (Read - select One)
	public MemberDTO getMemberById(Integer id) {
		for (MemberDTO member : list) {
			if(member.getId() == id) 
				return member;
		}
		return null;
	}
	
	// 입력 (Create - Insert)
	public MemberDTO postMember(MemberDTO memberDTO) {
		list.add(memberDTO);
		
		// 같은 primary key를 가진 객체를 저장을 시도해서 오류가 나면 어떻게 처리해줄건지
		return memberDTO;
	}
	
	// 수정(객체 교체)
	public ResponseEntity<?> putMember(Integer id, MemberDTO memberDTO) {
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i).getId() == id) {
				list.remove(i);
				list.add(i, memberDTO);
				return ResponseEntity.ok(memberDTO);
			}
		}
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body("수정 실패 : 데이터가 존재하지 않습니다.");
	}
	
	// 수정(일부 정보 수정)
	public ResponseEntity<?> patchMember(Integer id, MemberDTO memberDTO) {
		MemberDTO member = null;
		String name = memberDTO.getName();
		String pass = memberDTO.getPass();
		Date regidate = memberDTO.getRegidate(); // Null인 경우는 어떻게 될까?
		
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i).getId() == id)
				member = list.get(i);
		}

		if (member == null)
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("수정 실페: 데이터가 존재하지 않습니다.");
		
		if(name != null && !member.getName().equals(name))
			member.setName(name);
		if(pass != null && !member.getPass().equals(pass))
			member.setPass(pass);
		if(regidate != null && !member.getRegidate().equals(regidate))
			member.setRegidate(regidate);
		
		return ResponseEntity.ok(member);
	}
	
	// 삭제(Delete - delete)
	public void deleteMember(Integer id) {
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i).getId() == id)
				list.remove(i);
		}
	}
}