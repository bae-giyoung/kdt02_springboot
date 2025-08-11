package com.rubypaper;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.rubypaper.domain.Board;
import com.rubypaper.domain.Member;
import com.rubypaper.persistence.BoardRepository;
import com.rubypaper.persistence.MemberRepository;

@SpringBootTest
public class RelationMappingTest {
	@Autowired private BoardRepository boardRepo;
	@Autowired private MemberRepository memberRepo;
		
	//@Test
	public void testManyToOneInsert() {
		Member member1 = new Member();
		member1.setId("member1");
		member1.setPassword("member111");
		member1.setName("둘리");
		member1.setRole("User");
		//memberRepo.save(member1); // 해주지 않으면 비영속 상태, 해주면 영속상태
		
		Member member2 = new Member();
		member2.setId("member2");
		member2.setPassword("member222");
		member2.setName("도우너");
		member2.setRole("Admin");
		//memberRepo.save(member2);
		
		for(int i=1; i<=3; i++) {
			Board board = new Board();
			board.setMember(member1);
			board.setTitle("둘리가 등록한 게시글 " + i);
			board.setContent("둘리가 등록한 내용 " + i);
			board.setCreateDate(new Date());
			board.setCnt(0L);
			//boardRepo.save(board);
		}
		// Entity의 cascade 설정 확인용: member만 memberRepo에 저장을 했는데, 연결된 엔티티 board까지 db에 저장이 됨!
		memberRepo.save(member1); 
		
		for(int i=1; i<=3; i++) {
			Board board = new Board();
			board.setMember(member2);
			board.setTitle("도우너가 등록한 게시글 " + i);
			board.setContent("도우너가 등록한 내용 " + i);
			board.setCreateDate(new Date());
			board.setCnt(0L);
			//boardRepo.save(board);
		}
		memberRepo.save(member2); // Entity의 cascade 설정 확인
		
	}
	
	//@Transactional// @ManyToOne(fetch = FetchType.LAZY)의 해결 방안으로 사용했는데, 테스트에서만 transaction 설정이 달라서 오류나므로 필요!
	//@Test
	public void testManyToOneSelect() {
		Board board = boardRepo.findById(5L).get();
		System.out.println("[ " + board.getSeq() + "번 게시글 정보 ]");
		System.out.println("제목 : " + board.getTitle());
		System.out.println("내용 : " + board.getContent());
		System.out.println("작성자 : " + board.getMember().getName());
		System.out.println("작성자 권한 : " + board.getMember().getRole());
		
	}
	
	//@Test
	public void testTwoWayMapping() {
		Member member = memberRepo.findById("member1").get();
		System.out.println("===============================");
		System.out.println(member.getName() + "가(이) 저장한 게시글 목록");
		System.out.println("===============================");
		
		List<Board> list = member.getBoardList();
		for(Board board : list) {
			System.out.println(board.toString());
		}
		
		// 양방향 참조에서 Lombok의 @ToString으로 인해 순환 참조 발생 -> StackOverflowError
	}
	
	@Test
	public void testCascadeDelete() {
		memberRepo.deleteById("member2");
	}
}
