//package com.rubypaper;
//
//import java.util.Date;
//
//import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
//import org.junit.jupiter.api.TestMethodOrder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import com.rubypaper.domain.Board;
//import com.rubypaper.persistence.BoardRepository;
//
////@SpringBootTest
////@TestMethodOrder(OrderAnnotation.class)
//public class BoardRepositoryTest {
//	@Autowired
//	private BoardRepository boardRepo;
//	
//	// Spring Boot의 JPA API는 메서드 단위로 Transaction을 내부적으로 설정한다. 
//	// => 그래서 보통은 Transaction 설정이 필요없지만, 하지만 명시적으로 코드를 작성할 있음!
//	//@Test
//	//@Order(1)
//	public void testInsertBoard() {
//		Board board = new Board();
//		board.setTitle("두 번째 게시글");
//		board.setWriter("터스터");
//		board.setContent("잘 등록되나요?");
//		board.setCreateDate(new Date());
//		board.setCnt(0L);
//		boardRepo.save(board); // save를 해야 db에 반영됨! 내부적으로 Transaction등을 한다.
//	}
//	
//	//@Test
//	//@Order(2)
//	public void testGetBoard() {
//		Board board = boardRepo.findById(4L).get();
//		System.out.println(board);
//	}
//	
//	//@Test
//	//@Order(3)
//	public void testUpdateBoard() {
//		System.out.println("== 1번 게시글 조회 ==");
//		Board board = boardRepo.findById(4L).get();
//		System.out.println("== 게시글 제목 수정 ==");
//		board.setTitle("제목을 수정했습니다.");
//		boardRepo.save(board);
//	}
//	
//	//@Test
//	//@Order(4)
//	public void testDeleteBoard() {
//		boardRepo.deleteById(5L);
//	}
//	
//	public static void main(String[] args) {
//		
//	}
//}
