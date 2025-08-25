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
import edu.pnu.domain.Role;
import edu.pnu.persistence.MemberRepository;
import edu.pnu.util.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JWTAuthorizationFilter extends OncePerRequestFilter {
	private final MemberRepository memberRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		// 요청 헤더에서 JWT를 얻어온다
		String jwtToken =  request.getHeader(HttpHeaders.AUTHORIZATION);
		
		if(jwtToken == null || !jwtToken.startsWith(JWTUtil.prefix)) {
			filterChain.doFilter(request, response);
			return;
		}
		
		// 토큰에서 username 추출
		String username = JWTUtil.getClaim(jwtToken, JWTUtil.usernameClaim);
		User user = null;
		
		if(username != null) { // != null
			Optional<Member> opt = memberRepository.findById(username);
			
			if(!opt.isPresent()) {
				System.out.println("[JWTAuthorizationFilter]not found user!");
				filterChain.doFilter(request, response);
				return;
			}
			
			Member member = opt.get();
			System.out.println("[JWTAuthorizationFilter] " + member);
			
			user = new User(member.getUsername(), member.getPassword(),
					AuthorityUtils.createAuthorityList(member.getRole().toString()));
		}
		else {
			String provider = JWTUtil.getClaim(jwtToken, JWTUtil.providerClaim);
			String email = JWTUtil.getClaim(jwtToken, JWTUtil.emailClaim);
			System.out.println("[JWTAuthorization]username:"+provider+"_"+email);
			
			user = new User(provider+"_"+email, "*****",
					AuthorityUtils.createAuthorityList(Role.ROLE_MEMBER.toString()));
		}
		
		// 인증 객체 생성: 사용자명과 권한 관리를 위한 정보를 입력(암호는 필요 없음)
		Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		
		// 시큐리티 세션에 등록
		SecurityContextHolder.getContext().setAuthentication(auth);
		
		// 다음 필터로 이동
		filterChain.doFilter(request, response);
	}
	
}
