package edu.pnu;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import edu.pnu.domain.Board;
import edu.pnu.persistence.mapper.BoardMapper;

@Component
public class DataInit implements ApplicationRunner {
	@Autowired
	private BoardMapper boardMapper;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		for(int i = 1; i <= 200; i++) {
			Board board = new Board();
			board.setTitle("테스트제목"+ i);
			board.setWriter("테스터");
			board.setContent("테스트내용" + i);
			board.setCreateDate(new Date());
			board.setCnt(0L);
			boardMapper.insertBoard(board);
		}
		
	}
	
}
