package edu.pnu.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.pnu.dao.LogDao;
import edu.pnu.dao.MemberDao;
import edu.pnu.domain.MemberDTO;

@Service
public class MemberService {
	@Autowired
	private MemberDao memberDao;
	
	@Autowired
	private LogDao logDao;
	
	// 검색 (Read - select All)
	@SuppressWarnings("unchecked") // 왜 List Collection만 이 annotation을 붙이라고 뜰까? 타입 캐스팅 다르게 해야 할까??
	public List<MemberDTO> getAllMember() {
		Map<String, Object> map = memberDao.getAllMember();
		logDao.addLog(map);
		return (List<MemberDTO>)map.get("result");
	}
	
	// 검색 (Read - select One)
	public MemberDTO getMemberById(Integer id) {
		Map<String, Object> map = memberDao.getMemberById(id);
		logDao.addLog(map);
		return (MemberDTO)map.get("result");
	}
	
	// 입력 (Create - Insert)
	public MemberDTO postMember(MemberDTO memberDTO) {
		Map<String, Object> map = memberDao.postMember(memberDTO);
		logDao.addLog(map);
		return (MemberDTO)map.get("result");
	}
	
	// 수정(객체 교체)
	public MemberDTO putMember(Integer id, MemberDTO memberDTO) {
		Map<String, Object> map = memberDao.putMember(id, memberDTO);
		logDao.addLog(map);
		return (MemberDTO)map.get("result");
	}
	
	// 수정(일부 정보 수정)
	public MemberDTO patchMember(Integer id, MemberDTO memberDTO) {
		Map<String, Object> map = memberDao.patchMember(id, memberDTO);
		logDao.addLog(map);
		return (MemberDTO)map.get("result");
	}
	
	// 삭제(Delete - delete)
	public int deleteMember(Integer id) {
		Map<String, Object> map = memberDao.deleteMember(id);
		logDao.addLog(map);
		return (int)map.get("result");
	}
}