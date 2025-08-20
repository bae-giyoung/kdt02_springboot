package edu.pnu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import edu.pnu.domain.Member;
import edu.pnu.persistence.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SecurityService implements UserDetailsService {
	
	@Autowired
	private final MemberRepository memberRepo;
	
	@Autowired
	private final PasswordEncoder encoder;

	// UserDetailsService 구현체를 스프링 컨테이너에 등록하면 서버가 자동 생성 패스워드를 만들지 않는다.
	// MemberRepository로 회원 정보를 조회하야 UserDetails 타입의 객체를 리턴한다.
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member member = memberRepo.findById(username).orElseThrow(() -> new UsernameNotFoundException("NOT FOUND: " + username));
		return new User(member.getId(), member.getPassword(), AuthorityUtils.createAuthorityList(member.getRole().toString()));
	}
	
	public void save(Member member) {
		member.setPassword(encoder.encode(member.getPassword()));
		memberRepo.save(member);
	}
	
}
