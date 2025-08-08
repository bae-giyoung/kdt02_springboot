package edu.pnu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.pnu.domain.Member;
import edu.pnu.persistence.LogRepository;
import edu.pnu.persistence.MemberRepository;

@Service
public class MemberService {
	@Autowired
	private MemberRepository memberRepo;
	
	@Autowired
	private LogRepository logRepo;

	public List<Member> getMembers() {
		return memberRepo.findAll();
	}

	public Member getMember(Integer id) {
		return memberRepo.findById(id).get();
	}

	public Member postMember(Member member) {
		return memberRepo.save(member);
	}

	public Member putMember(Integer id, Member member) {
		Member targetMember = memberRepo.findById(id).get();
		
		if(targetMember == null)
			return targetMember;
		
		member.setId(id); // 방어코드 id는 바뀌면 안되므로
		return memberRepo.save(member);
	}

	public Member patchMember(Integer id, Member member) {
		Member targetMember = memberRepo.findById(id).get();
		String name = member.getName();
		String pass = member.getPass();
		
		if(targetMember == null)
			return targetMember;
		
		if(name != null && !targetMember.getName().equals(name))
			targetMember.setName(name);
		if(pass != null && !targetMember.getPass().equals(pass))
			targetMember.setPass(pass);
		
		return memberRepo.save(targetMember);
	}

	public Member deleteMember(Integer id) {
		Member targetMember = memberRepo.findById(id).get();
		
		if(targetMember == null)
			return targetMember;
		
		memberRepo.delete(targetMember);
		
		return targetMember;
	}
	
}