package com.rubypaper.board.service;

import org.springframework.data.domain.Page;

import com.rubypaper.board.domain.Search;
import com.rubypaper.domain.Board;

public interface BoardService {
	void insertBoard(Board board);
	void updateBoard(Board board);
	void deleleBoard(Board board);
	Board getBoard(Board board);
	Page<Board> getBoardList(Search search);

}
