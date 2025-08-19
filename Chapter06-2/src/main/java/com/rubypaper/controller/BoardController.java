package com.rubypaper.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.rubypaper.domain.Board;
import com.rubypaper.domain.Member;
import com.rubypaper.sevice.BoardService;

//@RestController = @Controller + @ResponseBody
//@Controller는 View 이름(jsp 등)를 리턴한다. => application.properties에서 설정해뒀기 때문!
//여기서 Session은 JPA의 Session과 완전히 다른 것!
//Model객체에 "member"라는 이름으로 저장되는 데이터를 자동으로 Session에 등록하도록 지시하는 설정.
//컨트롤러의 핸들러가 호출될 때 세션에 "member"속성이 있는지 확인하고, 없다면 @ModelAttribute("member")로 지정된 메서드를 호출하여 리턴된 객체를 세션에 저장
@SessionAttributes("member")
@Controller
public class BoardController {
	@Autowired
	private BoardService boardService;
	
	@ModelAttribute("member") // 빈 객체라도 없으면 아래의 메서드들에서 바인딩 오류가 뜨기 때문에 빈 객체 만들어주는것!
	public Member setMember() {
		return new Member();
	}
	
	// 전체 게시글 목록
	@GetMapping("/getBoardList")
	public String getBoardList(@ModelAttribute("member") Member member, Model model) {
		if(member.getId() == null)
			return "redirect:login";
		
		List<Board> boardList = boardService.getBoardList();
		
		model.addAttribute("boardList", boardList);
		return "getBoardList";
	}
	
	// 게시글 삽입
	@GetMapping("/insertBoard")
	public String insertBoardView(@ModelAttribute("member") Member member) {
		if(member.getId() == null)
			return "redirect:login";
		
		return "insertBoard";
	}
	
	@PostMapping("/insertBoard")
	public String insertBoard(@ModelAttribute("member") Member member, Board board) {
		if(member.getId() == null)
			return "redirect:login";
		
		boardService.insertBoard(board);
		return "redirect:getBoardList";
	}
	
	// 상세 조회
	@GetMapping("/getBoard")
	public String getBoard(@ModelAttribute("member") Member member, Board board, Model model) {
		if(member.getId() == null)
			return "redirect:login";
		
		Board findBoard = boardService.getBoard(board);
		boardService.updateBoardCnt(findBoard);
		model.addAttribute("board", findBoard);
		return "getBoard";
	}
	
	// 게시글 수정
	@PostMapping("/updateBoard")
	public String updateBoard(@ModelAttribute("member") Member member, Board board) {
		if(member.getId() == null)
			return "redirect:login";
		
		boardService.updateBoard(board);
		return "redirect:getBoardList";
	}
	
	// 게시글 삭제
	@GetMapping("/deleteBoard")
	public String deleteBoard(@ModelAttribute("member") Member member, Board board) {
		if(member.getId() == null)
			return "redirect:login";
		
		boardService.deleteBoard(board);
		return "redirect:getBoardList";
	}
	
}