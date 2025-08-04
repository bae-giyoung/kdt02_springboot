package com.rubypaper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

//@SpringBootTest
@SpringBootTest(properties = {"author.name=Gurum", "author.age=45", "author.nation=Korea"}) // 여기서 외부 프로퍼티를 ㅇ 설정해 주면 이것이 우선이 된다!
public class PropertiesTest {
	@Autowired
	Environment environment;
	
	@Test
	public void testMethod() {
		System.out.println("이름: " + environment.getProperty("author.name")); // 위에서처럼 설정해 주지 않으면 application.properties에 설정한것 가져온다
		System.out.println("나이: " + environment.getProperty("author.age"));
		System.out.println("국가: " + environment.getProperty("author.nation")); // 예외가 안 뜨고 null이 리턴된다!
	}
}
