package com.rubypaper.service;

import com.rubypaper.config.SecurityUser;
import com.rubypaper.domain.Member;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rubypaper.persistence.MemberRepository;

@Service
public class BoardUserDetailsService implements UserDetailsService {

	@Autowired
	private MemberRepository memberRepo;

	// 여기서 반환하는 UserDetails 타입의 인스턴스가 IOC Container에 올라가면 자동 생성 암호가 나타나지 않는다.
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<Member> optional = memberRepo.findById(username);
		if(!optional.isPresent()) {
			throw new UsernameNotFoundException(username + " 사용자 없음");
		} else {
			Member member = optional.get();
			return new SecurityUser(member);
		}
	}
}
