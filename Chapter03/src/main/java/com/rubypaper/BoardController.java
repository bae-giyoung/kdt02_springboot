package com.rubypaper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rubypaper.domain.BoardVO;

@RestController // 스프링이 자동으로 인스턴스를 만들어 준다.
public class BoardController {
	
	public BoardController() {
		System.out.println("===> BoardController 생성");
	}
	
	@GetMapping("/hello")
	public String hello(String name) {
		return "Hello : " + name;
	}
	
	//@GetMapping("/getBoard") -> 같은 메서드의 같은 url이 있으면 오류 발생!
	public BoardVO getBoard() {
		BoardVO board = BoardVO.builder()
						.seq(1)
						.title("텍스트 제목...")
						.writer("테스터")
						.content("테스트 내용입니다.....")
						.createDate(new Date())
						.cnt(0)
						.build();
		return board;
	}
	
	@GetMapping("/getBoardList")
	public List<BoardVO> getBoardList() {
		List<BoardVO> boardList = new ArrayList<BoardVO>();
		for (int i = 1; i <= 10; i++) {
			BoardVO board = BoardVO.builder()
							.seq(1)
							.title("텍스트 제목...")
							.writer("테스터")
							.content("테스트 내용입니다.....")
							.createDate(new Date())
							.cnt(0)
							.build();
			boardList.add(board);
		}
		return boardList;
	}
	
	@GetMapping("/getBoard")
	public BoardVO board(Integer seq) {
		BoardVO board = new BoardVO();
		board.setSeq(seq);
		board.setWriter("테스터");
		return board;
	}
}
