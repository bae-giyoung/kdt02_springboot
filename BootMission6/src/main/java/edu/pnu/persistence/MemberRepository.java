package edu.pnu.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.Member;

//@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {
}
