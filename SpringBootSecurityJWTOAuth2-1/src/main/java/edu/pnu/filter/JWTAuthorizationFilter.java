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
	private final MemberRepository memberRepository; // 인가 설정을 위해 사용자의 Role 정보를 읽기 위한 객체 설정

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);// 요청 헤더에서 Authorization을 가져온다.
		if(jwtToken == null || !jwtToken.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		//String jwtToken = srcToken.replace("Bearer ", "");
		// 토큰에서 username 추출
		//String username = JWTUtil.getClaim(jwtToken);
		
		User user = null;
		String username = JWTUtil.getClaim(jwtToken, JWTUtil.usernameClaim); //String username = JWTUtil.getClaim(jwtToken, JWTUtil.usernameClaim);
		
		if(username == null) {// DB에서 찾기
			System.out.println("username:" + username);
			Optional<Member> opt = memberRepository.findById(username);
			
			if(!opt.isPresent()) {
				System.out.println("===> not found user!");
				filterChain.doFilter(request, response);
				return;
			}
			
			Member member = opt.get();
			System.out.println("===>member:" + member);
			
			user = new User(member.getUsername(), member.getPassword(),
					AuthorityUtils.createAuthorityList(member.getRole().toString()));
			
		} else { // DB에 저장하지 않는 방식일 때 찾기
			String provider = JWTUtil.getClaim(jwtToken, JWTUtil.providerClaim);
			String email = JWTUtil.getClaim(jwtToken, JWTUtil.emailClaim);
			user = new User(provider +"_"+ email, "abcd",
					AuthorityUtils.createAuthorityList("ROLE_USER"));
		}
		
		// Authorication 객체를 생성
		Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		
		// 시큐리티 세선에 등록
		SecurityContextHolder.getContext().setAuthentication(auth);
		
		// 다음 필터로 이동
		filterChain.doFilter(request, response);
	}

}
