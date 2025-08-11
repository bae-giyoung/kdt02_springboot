package com.rubypaper.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rubypaper.domain.Member;

public interface MemberRepository extends JpaRepository<Member, String> {
	// ===== N+1 문제 그대로 보기
	//@Query("select m from Member m")
	//List<Member> getMembers();
	
	// ===== Fetch Join으로 해당 메소드만 fetch=EAGER로 설정해라! 
	//@Query("select m from Member m Join Fetch m.boardList")
	//List<Member> getMembers();
	
	// ===== EntityGraph로 해당 메소드만 fetch=EAGER로 설정해라!
	//@EntityGraph(value="Member.boardList", type=EntityGraphType.LOAD)
	//@Query("select m from Member m")
	//List<Member> getMembers();
	
	// ===== Batch Size로 설정!
	@Query("select m from Member m")
	List<Member> getMembers();
}
