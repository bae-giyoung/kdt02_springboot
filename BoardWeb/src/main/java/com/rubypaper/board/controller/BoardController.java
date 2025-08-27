package com.rubypaper.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rubypaper.board.domain.Search;
import com.rubypaper.board.security.SecurityUser;
import com.rubypaper.board.service.BoardService;
import com.rubypaper.domain.Board;

@Controller
@RequestMapping("/board/")
public class BoardController {
	@Autowired
	private BoardService boardService;
	
	@RequestMapping("/getBoardList")
	public String getBoardList(Model model, Search search) {//Model model, Board board
		if(search.getSearchCondition() == null)
			search.setSearchCondition("TITLE");		
		if(search.getSearchKeyword() == null)
			search.setSearchKeyword("");
		
		Page<Board> boardList = boardService.getBoardList(search);
		model.addAttribute("boardList", boardList);
		return "board/getBoardList";
	}
	
	@GetMapping("/getBoard")
	public String getBoard(Board board, Model model) {
		model.addAttribute("board", boardService.getBoard(board));
		return "board/getboard";
	}
	
	@GetMapping("/insertBoard")
	public String insertBoardView() {
		//model.addAttribute("username", principal.getMember().getName()); // 게시글 등록 시 작성자 이름 나오게 해봄 -> 라이브러리로 변경
		return "board/insertBoard";
	}
	
	@PostMapping("/insertBoard")
	public String insertBoard(Board board, @AuthenticationPrincipal SecurityUser principal) {
		board.setMember(principal.getMember());
		boardService.insertBoard(board);
		return "redirect:getBoardList";
	}
	
	@PostMapping("/updateBoard")
	public String updateBoard(Board board) {
		boardService.updateBoard(board);
		return "forward:getBoardList"; // forward 안되지 않나?
	}
	
	@GetMapping("/deleteBoard")
	public String deleteBoard(Board board) {
		boardService.deleleBoard(board);
		return "forward:getBoardList";
	}
}
