package edu.pnu;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import edu.pnu.domain.Member;
import edu.pnu.domain.Role;
import edu.pnu.persistence.MemberRepository;
import lombok.RequiredArgsConstructor;

//@Component
@RequiredArgsConstructor
public class DataInit implements ApplicationRunner {
	private final MemberRepository memberRepo;
	private final PasswordEncoder encoder;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		memberRepo.save(Member.builder().id("member").name("홍길동")
							.password(encoder.encode("abcd")).role(Role.ROLE_MEMBER).enabled(true).build());
		
		memberRepo.save(Member.builder().id("manager").name("홍이동")
				.password(encoder.encode("abcd")).role(Role.ROLE_MANAGER).enabled(true).build());
		
		memberRepo.save(Member.builder().id("admin").name("홍삼동")
				.password(encoder.encode("abcd")).role(Role.ROLE_ADMIN).enabled(true).build());
	}
}
