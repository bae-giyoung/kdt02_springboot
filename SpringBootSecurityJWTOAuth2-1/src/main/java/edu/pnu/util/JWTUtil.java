package edu.pnu.util;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;

public class JWTUtil {
	private static final long ACCESS_TOKEN_MSEC = 24*60*(60*1000); // 1일
	public static final String JWT_KEY = "edu.pnu.jwtkey";
	private static final String prefix = "Bearer "; // JWT 토큰 헤더 문자열, 뒤에 빈 문자열 반드시 붙는다
	
	public static final String usernameClaim = "username";
	public static final String providerClaim = "provider"; // OAuth2 Provider
	public static final String emailClaim = "email";
	
	private static String getJWTSource(String token) {
		if(token.startsWith(prefix)) return token.replace(prefix, "");
		return token;
	}
	
	// JWT를 만들 때 호출 with DB
	public static String getJWT(String username) {
		String src = JWT.create()
						.withClaim(usernameClaim, username)
						.withExpiresAt(new Date(System.currentTimeMillis()+ACCESS_TOKEN_MSEC))
						.sign(Algorithm.HMAC256(JWT_KEY));
		return prefix + src;
	}
	
	// JWT를 만들 때 호출 without DB
	public static String getJWT(String provider, String email) {
		String src = JWT.create()
				.withClaim(providerClaim, provider)
				.withClaim(usernameClaim, email) // DB에 없으니까 username없이 emai이 username처럼 사용하게 된다는?
				.withExpiresAt(new Date(System.currentTimeMillis()+ACCESS_TOKEN_MSEC))
				.sign(Algorithm.HMAC256(JWT_KEY));
		return prefix + src;
	}
	
	// JWT에서 claim 추출할 때 호출
	public static String getClaim(String token, String cname) {
		String tok = getJWTSource(token);
		Claim claim = JWT.require(Algorithm.HMAC256(JWT_KEY)).build().verify(tok).getClaim(cname);
		if(claim.isMissing()) return null;
		return claim.asString();
	}
	
	// 유효기간 만료 여부
	public static boolean isExpired(String token) {
		String tok = getJWTSource(token);
		return JWT.require(Algorithm.HMAC256(JWT_KEY)).build().verify(tok).getExpiresAt().before(new Date());
	}


}
