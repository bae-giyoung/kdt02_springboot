package com.rubypaper.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.querydsl.core.BooleanBuilder;
import com.rubypaper.board.domain.Search;
import com.rubypaper.domain.Board;
import com.rubypaper.persistence.BoardRepository;
import com.rubypaper.board.domain.QBoard;

@Service
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	private BoardRepository boardRepo;

	@Override
	public void insertBoard(Board board) {
		boardRepo.save(board);
	}

	@Override
	public void updateBoard(Board board) {
		Board findBoard = boardRepo.findById(board.getSeq()).get();
		findBoard.setTitle(board.getTitle());
		findBoard.setContent(board.getContent());
		boardRepo.save(findBoard);
	}

	@Override
	public void deleleBoard(Board board) {
		boardRepo.deleteById(board.getSeq());
	}

	@Override
	public Board getBoard(Board board) {
		return boardRepo.findById(board.getSeq()).get();
	}

	@Override
	public Page<Board> getBoardList(Search search) {
		BooleanBuilder builder = new BooleanBuilder();
		
		//QBoard qboard = QBoard.board;
		Pageable pageable = PageRequest.of(0, 10, Sort.Direction.DESC, "seq");
		return boardRepo.getBoardList(pageable);
	}

}
