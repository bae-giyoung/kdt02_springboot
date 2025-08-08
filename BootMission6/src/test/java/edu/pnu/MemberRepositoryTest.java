package edu.pnu;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import edu.pnu.domain.Member;
import edu.pnu.persistence.MemberRepository;

@SpringBootTest
public class MemberRepositoryTest {
	@Autowired
	private MemberRepository memberRepo;
	
	@Test
	public void testGetMember() {
		Member member = memberRepo.findById(1).get();
		System.out.println(member);
	}
}
