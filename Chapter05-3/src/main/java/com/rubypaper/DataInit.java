package com.rubypaper;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.rubypaper.domain.Board;
import com.rubypaper.domain.Member;
import com.rubypaper.persistence.BoardRepository;
import com.rubypaper.persistence.MemberRepository;

@Component
public class DataInit implements ApplicationRunner {
	@Autowired
	private MemberRepository memberRepo;
	
	@Autowired
	private BoardRepository boardRepo;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		for(int i=1; i<=10; i++) {
			String name = "member" + i;
			
			Member member = new Member();
			member.setId(name);
			member.setPassword(name+"pass");
			member.setName(name+"name");
			member.setRole("User");
			memberRepo.save(member);
			
			for(int j=1; j<=2; j++) {
				Board board = new Board();
				board.setMember(member);
				board.setTitle(name+" 등록 게시글 " + j);
				board.setContent(name+" 등록 게시글 내용 "+j);
				board.setCreateDate(new Date());
				board.setCnt(0L);
				boardRepo.save(board);
			}
		}
	}
}
