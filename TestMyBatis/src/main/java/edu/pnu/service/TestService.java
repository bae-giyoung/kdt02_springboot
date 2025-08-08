package edu.pnu.service;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.pnu.domain.Board;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TestService {
	private final EntityManager em;
	
	public List<Board> getBoardList(String cond, String keyword) {
		// 질의문 생성
		String sql = "select b from Board b where 1=1";
		StringBuilder sb = new StringBuilder(sql);
		if(cond != null) {
			if(cond.equals("title"))
				sb.append(" AND b.title like '%" +keyword+ "%'");
			else if(cond.equals("content"))
				sb.append(" AND b.content like '%" +keyword+ "%'");
		}
		sb.append(" ORDER BY b.id ASC");
		
		// 질의 객체 생성
		TypedQuery<Board> query = em.createQuery();
	}
}
