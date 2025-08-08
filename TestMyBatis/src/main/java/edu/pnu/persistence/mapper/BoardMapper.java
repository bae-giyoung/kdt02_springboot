package edu.pnu.persistence.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import edu.pnu.domain.Board;

@Mapper
public interface BoardMapper{
	List<Board> getBoardAllList();
	List<Board> getBoardList(String cond, String keyword);
	void insertBoard(Board board);
}