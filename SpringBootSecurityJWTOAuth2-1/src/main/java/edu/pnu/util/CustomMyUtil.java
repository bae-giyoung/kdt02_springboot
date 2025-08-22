package edu.pnu.util;

import java.util.Map;

import org.springframework.security.oauth2.core.user.OAuth2User;

public class CustomMyUtil {
	//
	@SuppressWarnings("unchecked")
	public static String getUsernameFromOAuth2User(OAuth2User user) {
		
		// 로그인 성공 후 인증 서버에서 보내준 속성 정보 추출
		Map<String, Object> attmap = user.getAttributes();
		String userString = (String)user.toString();
		
		String ret = null;
		
		if(userString.contains("googleapis.com")) {
			ret = "Google_" + attmap.get("name") + "_" + attmap.get("sub");
		} else if (userString.contains("github.com")) {
			ret = "Github_" + attmap.get("login") + "_" + attmap.get("id");
		} else if (userString.contains("facebook.com")) {
			ret = "Facebook_" + attmap.get("name") + "_" + attmap.get("id");
		} else if (userString.contains("response=")) {
			Map<String, Object> map = (Map<String, Object>)attmap.get("response");
			ret = "Naver_" + map.get("name") + "_" + map.get("id");
		} else if (userString.contains("kakaocdn.net")) {
			Map<String, Object> map = (Map<String, Object>)attmap.get("properties");
			ret = "Naver_" + map.get("nickname") + "_" + map.get("id");
		}
		
		// ret가 null이 아니면 ",", " "(공백)를 제거
		if(ret != null) {
			ret = ret.replaceAll(",", "_").replaceAll(" ", "_");
		}
		return ret;
	}
}
