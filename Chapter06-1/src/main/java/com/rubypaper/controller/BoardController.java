package com.rubypaper.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.rubypaper.domain.Board;
import com.rubypaper.sevice.BoardService;

//@RestController는 무엇을 리턴?
//@Controller는 View 이름(jsp)를 리턴한다. => application.properties에서 설정해뒀기 때문!
@Controller
public class BoardController {
	@Autowired
	private BoardService boardService;
	
	// 전체 게시글 목록
	@GetMapping("/getBoardList")
	public String getBoardList(Model model) {
		List<Board> boardList = boardService.getBoardList();
		
		model.addAttribute("boardList", boardList);
		return "getBoardList";
	}
	
	// 게시글 삽입
	@GetMapping("/insertBoard")
	public String insertBoardView() {
		return "insertBoard";
	}
	
	@PostMapping("/insertBoard")
	public String insertBoard(Board board) {
		boardService.insertBoard(board);
		return "redirect:getBoardList";
	}
	
	// 상세 조회
	@GetMapping("/getBoard")
	public String getBoard(Board board, Model model) {
		Board findBoard = boardService.getBoard(board);
		boardService.updateBoardCnt(findBoard);
		model.addAttribute("board", findBoard);
		return "getBoard";
	}
	
	// 게시글 수정
	@PostMapping("/updateBoard")
	public String updateBoard(Board board) {
		boardService.updateBoard(board);
		return "redirect:getBoardList";
	}
	
	// 게시글 삭제
	@GetMapping("/deleteBoard")
	public String deleteBoard(Board board) {
		boardService.deleteBoard(board);
		return "redirect:getBoardList";
	}
	
	@GetMapping("/hello")
	public void hello(Model model) {
		model.addAttribute("greeting", "HEllo 타임리프!");
	}
	
}