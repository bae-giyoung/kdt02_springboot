package com.rubypaper;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.rubypaper.domain.Board;

public class JPAClient {
//	public static void main(String[] args) {
//		
//		// EntityManagerFactory 생성
//		EntityManagerFactory emf = Persistence.createEntityManagerFactory("Chapter04");
//		
//		// EntityManager 생성
//		EntityManager em = emf.createEntityManager();
//		
//		// Transaction 생성 > All Or Not 한 덩어리 > 더 정확한 개념 정리!
//		EntityTransaction tx = em.getTransaction();
//		
//		try {
//			// Transaction 시작
//			tx.begin();
//			
//			// DB에 저장할 객체 생성
//			Board board = new Board();
//			board.setTitle("JPA 제목");
//			board.setWriter("관리자");
//			board.setContent("JPA 글 등록 잘 되네요");
//			board.setCreateDate(new Date());
//			board.setCnt(0L);
//			
//			// 글 등록
//			em.persist(board);
//			
//			// Transaction commit
//			tx.commit();
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			
//			// Transaction Rollback
//			tx.rollback();
//		} finally {
//			// 사용한 리소스 객체 닫기
//			em.close();
//			emf.close();
//		}
//	}
	
//	public static void main(String[] args) {
//		
//		// EntityManagerFactory 생성
//		EntityManagerFactory emf = Persistence.createEntityManagerFactory("Chapter04");
//		
//		// EntityManager 생성
//		EntityManager em = emf.createEntityManager();
//		
//		try {
//			
//			// 글 상세 조회
//			Board searchBoard = em.find(Board.class, 1L);
//			System.out.println("---> " + searchBoard.toString());
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			
//		} finally {
//			// 사용한 리소스 객체 닫기
//			em.close();
//			emf.close();
//		}
//	}
	
	
//	public static void main(String[] args) {
//		
//		// EntityManagerFactory 생성
//		EntityManagerFactory emf = Persistence.createEntityManagerFactory("Chapter04"); // 지속적으로 사용
//		
//		// EntityManager 생성
//		EntityManager em = emf.createEntityManager(); // 수시로 만들고 버린다. 보통 transaction 단위로
//		
//		try {
//			// JPQL
//			List<Board> list = em.createQuery("select b from Board b", Board.class).getResultList();
//			list.stream().forEach(System.out::println);
//			
//			// Native Query
//			@SuppressWarnings("unchecked")
//			List<Board> list1 = em.createNativeQuery("select * from board", Board.class).getResultList();
//			list1.stream().forEach(System.out::println);
//			
//			// 글 상세 조회
//			Board searchBoard = em.find(Board.class, 1L);
//			System.out.println("---> " + searchBoard.toString());
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			
//		} finally {
//			// 사용한 리소스 객체 닫기
//			em.close();
//			emf.close();
//		}
//	}
	
	public static void selectBoardList(EntityManagerFactory emf) {
		
		// EntityManager 생성: 수시로 만들고 버린다. 보통 transaction 단위로
		EntityManager em = emf.createEntityManager();
		
		try {
			// JPQL
			List<Board> list = em.createQuery("select b from Board b", Board.class).getResultList();
			list.stream().forEach(System.out::println);
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			// 사용한 리소스 객체 닫기
			em.close();
		}
	}
	
	// 글 상세 조회
	public static void selectBoard(EntityManagerFactory emf) {
		EntityManager em = emf.createEntityManager();
		
		try {
			Board searchBoard = em.find(Board.class, 5L);
			System.out.println("---> " + searchBoard.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
	}
	
	// 삽입
	public static void insertBoard(EntityManagerFactory emf) {
		EntityManager em = emf.createEntityManager();
		
		EntityTransaction tx = em.getTransaction();
		
		try {
			// 트랜섹션 시작
			tx.begin();
			
			Board board = new Board();
			board.setTitle("JPA 제목");
			board.setWriter("관리자");
			board.setContent("Method: JPA 글 등록 잘 되네요");
			board.setCreateDate(new Date());
			board.setCnt(0L);
			
			em.persist(board); // 영속성 > 영속성 컨텍스트 생각!!!!!
			tx.commit(); // db에 반영!!!!
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("게시글 등록 중 오류가 발생했습니다.");
			
			// 롤백
			tx.rollback();
			
		} finally {
			em.close();
		}
	}
	
	// 수정
	public static void updateBoard(EntityManagerFactory emf) {
		EntityManager em = emf.createEntityManager();
		
		EntityTransaction tx = em.getTransaction();
		
		try {
			tx.begin();
			
			Board board = em.find(Board.class, 5L);
			board.setTitle("검색한 게시글의 제목 수정");
			
			tx.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("게시글 수정 중 오류가 발생했습니다.");
			
			// 롤백
			tx.rollback();
			
		} finally {
			em.close();
		}
	}
	
	// 삭제
	public static void deleteBoard(EntityManagerFactory emf) {
		EntityManager em = emf.createEntityManager();
		
		EntityTransaction tx = em.getTransaction();
		
		try {
			tx.begin();
			
			Board board = em.find(Board.class, 5L);
			em.remove(board);
			
			tx.commit(); // db에 반영
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("게시글 삭제 중 오류가 발생했습니다.");
			
			// 롤백
			tx.rollback();
			
		} finally {
			em.close();
		}
	}
	
	public static void main(String[] args) {
		
		// EntityManagerFactory 생성
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("Chapter04"); // 지속적으로 사용
		
		selectBoardList(emf);
		selectBoard(emf);
		insertBoard(emf);
		updateBoard(emf);
		deleteBoard(emf);
		
		emf.close();
	}
}
