package com.rubypaper;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;

import com.rubypaper.domain.BoardVO;

//1) @WebMvcTest // 이것은 @Controller만 초기화. 메모리에 올림. @SpringBootTest(webEnvironment=WebEnvironment.MOCK)없이 사용!
//2) @SpringBootTest(webEnvironment=WebEnvironment.MOCK)
//2) @AutoConfigureMockMvc
//3) @SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class BoardControllerTest {
	
	// MockMvb 객체를 사용하는 코드들은 내장 톰캣을 구동해서 사용하는 테스트에서는 동작하지 않아서 주석 처리.  
//	@Autowired
//	private MockMvc mockMvc;
//	
//	//@Test
//	public void testHello() throws Exception {
//		mockMvc.perform(get("/hello").param("name", "둘리"))//http://localhost:8080/hello?name=둘리 (get요쳥)
//		.andExpect(status().isOk())// isOk 니까 200
//		.andExpect(content().string("Hello : 둘리"))
//		.andDo(print()); // 성공해도 메시지 출려되도록!
//	}
//	
//	@Test
//	public void testHelloJson2() throws Exception {
//		ObjectMapper objectMapper = new ObjectMapper();
//		
//		MvcResult mvcResult = mockMvc.perform(get("/getBoard").param("seq", "100"))
//				.andExpect(status().isOk())
//				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
//				.andExpect(jsonPath("$.writer").value("테스터"))
//				.andDo(print())
//				.andReturn();
//		
//		String jsonString = mvcResult.getResponse().getContentAsString();
//		BoardVO board = objectMapper.readValue(jsonString, BoardVO.class);
//		assertEquals(board.getSeq(), 100);
//	}
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Test
	public void testHello() throws Exception {
		String result = restTemplate.getForObject("/hello?name=둘리", String.class); // resonseType
		
		assertEquals("Hello : 둘리", result);
		
		BoardVO board = restTemplate.getForObject("/getBoard?seq=1", BoardVO.class); // resonseType
		
		assertEquals("테스터", board.getWriter());
	}
}
