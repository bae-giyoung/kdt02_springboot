package edu.pnu.filter;

import java.io.IOException;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.filter.OncePerRequestFilter;

import edu.pnu.domain.Member;
import edu.pnu.persistence.MemberRepository;
import edu.pnu.util.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JWTAuthorizationFilter extends OncePerRequestFilter {
	private final MemberRepository memberRepository; // authwired.....? 인가 설정을 위해 사용자의 Role 정보를 읽기 위한 객체 설정

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String srcToken = request.getHeader(HttpHeaders.AUTHORIZATION);// 요청 헤더에서 Authorization을 가져온다.
		if(srcToken == null || !srcToken.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		String jwtToken = srcToken.replace("Bearer ", "");
		
		// 토큰에서 username 추출
		String username = JWTUtil.getClaim(jwtToken);
		
		Optional<Member> opt = memberRepository.findById(username);
		if(!opt.isPresent()) {
			filterChain.doFilter(request, response);
			return;
		}
		Member findmember = opt.get();
		
		User user = new User(findmember.getUsername(), findmember.getPassword(),
							AuthorityUtils.createAuthorityList(findmember.getRole().toString()));
		
		// Authorication 객체를 생성
		Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		
		// 시큐리티 세선에 등록
		SecurityContextHolder.getContext().setAuthentication(auth);
		
		// 다음 필터로 이동
		filterChain.doFilter(request, response);
	}

}
