package edu.pnu.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.util.JDBConnect;
import edu.pnu.domain.MemberDTO;

public class MemberDao extends JDBConnect {
	/*
	 * [테스트 시 유의]
	 * 입력할 수 있는 길이가 짧다
	 * id int auto_increment primary key
	 * pass varchar(10)
	 * name varchar(20)
	 * regidate date default (curdate()) not null
	 */
	
	// 검색: 모든 리스트 가져오기
	public Map<String, Object> getAllMember() {
		List<MemberDTO> list = new ArrayList<>();
		Statement stmt = null;
		ResultSet rs = null;
		String query = "select * from member";
		
		Map<String, Object> map = new HashMap<>();
		map.put("method", "get");
		map.put("sqlstring", query);
		
		try {
			stmt = getCon().createStatement();
			rs = stmt.executeQuery(query);
			
			while(rs.next()) {
				MemberDTO dto = MemberDTO.builder()
									.id(rs.getInt("id"))
									.name(rs.getString("name"))
									.pass(rs.getString("pass"))
									.regidate(rs.getDate("regidate")).build();
				list.add(dto);
				map.put("success", true);
				map.put("result", list);
			}
			
		} catch(Exception e) {
			map.put("success", false);
			map.put("result", null);
			System.out.println("전체 회원 검색 중 오류가 발생하였습니다.");
			e.printStackTrace();
		} finally {
				try {	if(rs != null) rs.close();	} catch (Exception e) {	e.printStackTrace();}
				try {	if(stmt != null) stmt.close();	} catch (Exception e) {	e.printStackTrace();}
		}
		
		return map;
	}
	
	// 검색: 하나만
	public Map<String, Object> getMemberById(Integer id) {
		MemberDTO dto = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		String query = "select * from member where id=?";
		
		Map<String, Object> map = new HashMap<>();
		map.put("method", "get");
		
		
		try {
			psmt = getCon().prepareStatement(query);
			psmt.setInt(1, id);
			map.put("sqlstring", psmt.toString().split(": ")[1]);
			rs = psmt.executeQuery();
			
			if(rs.next()) {
				dto = MemberDTO.builder()
						.id(rs.getInt("id"))
						.name(rs.getString("name"))
						.pass(rs.getString("pass"))
						.regidate(rs.getDate("regidate")).build();
			}
			map.put("success", true);
			map.put("result", dto);
			
		} catch(Exception e) {
			map.put("success", false);
			map.put("result", null);
			System.out.println("회원 검색 중 오류가 발생하였습니다.");
			e.printStackTrace();
		} finally {
				try {	if(rs != null) rs.close();	} catch (Exception e) {	e.printStackTrace();}
				try {	if(psmt != null) psmt.close();	} catch (Exception e) {	e.printStackTrace();}
		}
		
		return map; // 존재하지 않는 경우 null을 반환하는데 어떻게 처리해줄지? => DAO에서는 객체(null)를 리턴하고, Controller에서 ResponseEntity를 Return 해주면 된다!!!!!
	}
	
	// 입력
	public Map<String, Object> postMember(MemberDTO memberDTO) {
		MemberDTO member = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		String query = "insert into member (pass, name) values (?, ?)";
		int cnt = 0;
		
		Map<String, Object> map = new HashMap<>();
		map.put("method", "post");

		try {
			psmt = getCon().prepareStatement(query, Statement.RETURN_GENERATED_KEYS); // RETURN_GENERATED_KEYS
			psmt.setString(1, memberDTO.getPass());
			psmt.setString(2, memberDTO.getName());
			map.put("sqlstring", psmt.toString().split(": ")[1]);
			cnt = psmt.executeUpdate();
			
			if(cnt == 1) {
				rs = psmt.getGeneratedKeys(); // 이렇게 가져오면
				rs.next();
				int id = rs.getInt(1);
				System.out.println(id);
				member = (MemberDTO)(getMemberById(id).get("result"));
				map.put("success", true);
				map.put("result", member);
			} else {
				map.put("success", false);
				map.put("result", null);				
			}
			
		} catch(Exception e) {
			map.put("success", false);
			map.put("result", null);
			System.out.println("데이터 등록 중 오류가 발생하였습니다.");
			e.printStackTrace();
		} finally {
			try {	if(rs != null) rs.close();	} catch (Exception e) {	e.printStackTrace();}
			try {	if(psmt != null) psmt.close();	} catch (Exception e) {	e.printStackTrace();}
		}
		
		return map;
	}
	
	// 수정(객체 교체)
	public Map<String, Object> putMember(Integer id, MemberDTO memberDTO) {
		PreparedStatement psmt = null;
		String query = "update member set pass=?, name=?, regidate=? where id=?";
		int cnt = 0;
		
		Map<String, Object> map = new HashMap<>();
		map.put("method", "put");
		
		
		try {
			psmt = getCon().prepareStatement(query);
			psmt.setString(1, memberDTO.getPass());
			psmt.setString(2, memberDTO.getName());
			psmt.setDate(3, (Date)memberDTO.getRegidate());
			psmt.setInt(4, id);
			map.put("sqlstring", psmt.toString().split(": ")[1]);
			
			cnt = psmt.executeUpdate();
			
			if(cnt == 0) {
				map.put("result", null);
				map.put("success", false);
			} else {
				map.put("result", (MemberDTO)getMemberById(id).get("result"));
				map.put("success", true);				
			}
			
		} catch(Exception e) {
			map.put("result", null);
			map.put("success", false);
			System.out.println("데이터 수정(교체) 중 오류가 발생하였습니다.");
			e.printStackTrace();
		} finally {
			try {	if(psmt != null) psmt.close();	} catch (Exception e) {	e.printStackTrace();}
		}
		
		return map;
	}
	
	// 수정(일부 수정)
	public Map<String, Object> patchMember(Integer id, MemberDTO memberDTO) {
		Statement stmt = null;
		String pass = memberDTO.getPass();
		String name = memberDTO.getName();
		Date regidate = memberDTO.getRegidate();
		String query = "update member set ";
		int cnt = 0;
		
		if(pass != null)
			query += " pass='" + pass + "',";
		if(name != null)
			query += " name='" + name + "',";
		if(regidate != null)
			query += " regidate='" + regidate + "'";
			
		query += " where id=" + id;
		query = query.replace(", where", " where"); // 모두 null이라면 update member set  where id=?가 되는데 그 경우엔 쿼리 실패하면 되니 상관 없음.
			
		
		Map<String, Object> map = new HashMap<>();
		map.put("method", "patch");
		map.put("sqlstring", query);
		
		
		try {
			stmt = getCon().createStatement();
			cnt = stmt.executeUpdate(query);
			
			if (cnt == 0) {
				map.put("success", false);
				map.put("result", null);
			} else {
				map.put("success", true);
				map.put("result", (MemberDTO)getMemberById(id).get("result"));
			}
			
		} catch(Exception e) {
			map.put("success", false);
			map.put("result", null);
			System.out.println("데이터 수정(일부 수정) 중 오류가 발생하였습니다.");
			e.printStackTrace();
		} finally {
			try {	if(stmt != null) stmt.close();	} catch (Exception e) {	e.printStackTrace();}
		}
		
		return map;
	}
	
	// 삭제
	public Map<String, Object> deleteMember(Integer id) {
		PreparedStatement psmt = null;
		String query = "delete from member where id=?";
		int cnt = 0;
		
		Map<String, Object> map = new HashMap<>();
		map.put("method", "delete");
		
		try {
			psmt = getCon().prepareStatement(query);
			psmt.setInt(1, id);
			map.put("sqlstring", psmt.toString().split(": ")[1]); // query execute 전에 넣어주는 이유! -> execute 문은 오류가 날 수 있기 때문.
			cnt = psmt.executeUpdate();
			
			if(cnt == 0) {
				map.put("success", false);				
			} else {				
				map.put("success", true);
			}
			
		} catch(Exception e) {
			map.put("success", false);
			System.out.println("데이터 삭제 중 오류가 발생하였습니다.");
			e.printStackTrace();
		} finally {
			map.put("result", cnt);
			try {	if(psmt != null) psmt.close();	} catch (Exception e) {	e.printStackTrace();}			
		}
		
		return map;
	}
	
}