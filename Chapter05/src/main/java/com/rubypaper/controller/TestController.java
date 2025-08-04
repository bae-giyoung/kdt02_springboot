package com.rubypaper.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rubypaper.domain.Board;
import com.rubypaper.persistence.BoardRepository;

@RestController
@RequestMapping("/api")
public class TestController {
	
	@Autowired
	private BoardRepository boardRepo;
	
	@GetMapping("/board")
	public List<Board> getBoards() {
		return boardRepo.findAll();
	}
	
	@GetMapping("/board/{seq}")
	public Board getBoard(@PathVariable Long seq) {
		return boardRepo.findById(seq).get();
	}
	
	@PostMapping("/board")
	public Board postBoard(@RequestBody Board board) {
		return boardRepo.save(board); // 실패할 경우 리턴해주는 값이 뭘까?
	}
	
	@PutMapping("/board/{seq}")
	public Board putBoard(@PathVariable Long seq, @RequestBody Board board) {
		board.setSeq(seq); // 예외처리!!!!
		return boardRepo.save(board);
	}
	
	@PatchMapping("/board/{seq}")
	public Board patchBoard(@PathVariable Long seq, Board board) {
		Board targetBoard = boardRepo.findById(seq).get();
		String title = board.getTitle();
		String writer = board.getTitle();
		String content = board.getTitle();
		
		if(targetBoard.getTitle() == null || targetBoard.getTitle().equals(title))
			targetBoard.setTitle(title);
		if(targetBoard.getWriter() == null || targetBoard.getWriter().equals(writer))
			targetBoard.setWriter(writer);
		if(targetBoard.getContent() == null || targetBoard.getContent().equals(content))
			targetBoard.setContent(content);
		
		try {
			boardRepo.save(targetBoard);
			return targetBoard;			
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@DeleteMapping("/board/{seq}")
	public Board deleteBoard(@PathVariable Long seq) {
		
		try {
			Board board = boardRepo.findById(seq).get();
			boardRepo.deleteById(seq);
			return board;
		} catch (Exception e) {
			System.out.println("게시물 삭제 작업 중 오류 발생");
			return null;
		}
		
	}
}