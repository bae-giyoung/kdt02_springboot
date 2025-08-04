package edu.pnu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.pnu.dao.MemberDao;
import edu.pnu.domain.MemberDTO;
import lombok.RequiredArgsConstructor;

@Service // 이것은 서비스를 위한 @Component이다
@RequiredArgsConstructor // final이 붙은
public class MemberService {
	
	//@Autowired
	//private MemberDao dao;
	
	private final MemberDao dao;
	
	// 검색 (Read - select All)
	public List<MemberDTO> getAllMember() {
		return dao.getAllMember();
	}
	
	// 검색 (Read - select One)
	public MemberDTO getMemberById(Integer id) {
		return dao.getMemberById(id);
	}
	
	// 입력 (Create - Insert)
	public MemberDTO postMember(MemberDTO memberDTO) {
		return dao.postMember(memberDTO);
	}
	
	// 수정(객체 교체)
	public MemberDTO putMember(Integer id, MemberDTO memberDTO) {
		return dao.putMember(id, memberDTO);
	}
	
	// 수정(일부 정보 수정)
	public MemberDTO patchMember(Integer id, MemberDTO memberDTO) {
		return dao.patchMember(id, memberDTO);
	}
	
	// 삭제(Delete - delete)
	public int deleteMember(Integer id) {
		return dao.deleteMember(id);
	}
}