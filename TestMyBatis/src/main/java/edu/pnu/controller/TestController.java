package edu.pnu.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.Board;
import edu.pnu.persistence.mapper.BoardMapper;
import edu.pnu.service.TestService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TestController {
	private final BoardMapper mapper;
	private final TestService testService;
	
	@GetMapping("/board")
	public List<Board> getBoards(String cond, String keyword) {
		if(cond == null) {
			return mapper.getBoardAllList();
		}
		return mapper.getBoardList(cond, keyword);
	}
	
	@GetMapping("/boardjpql")
	public List<Board> getBoardsByJPQL(String cond, String keyword) {
		return testService.getBoardList(cond, keyword);
	}
}
