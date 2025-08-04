package edu.pnu.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
	public List<MemberDTO> getAllMember() {
		List<MemberDTO> list = new ArrayList<>();
		Statement stmt = null;
		ResultSet rs = null;
		String query = "select * from member";
		
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
			}
			
		} catch(Exception e) {
			System.out.println("전체 회원 검색 중 오류가 발생하였습니다.");
			e.printStackTrace();
		} finally {
				try {	if(rs != null) rs.close();	} catch (Exception e) {	e.printStackTrace();}
				try {	if(stmt != null) stmt.close();	} catch (Exception e) {	e.printStackTrace();}
		}
		
		return list;
	}
	
	// 검색: 하나만
	public MemberDTO getMemberById(Integer id) {
		MemberDTO dto = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		String query = "select * from member where id=?";
		
		try {
			psmt = getCon().prepareStatement(query);
			psmt.setInt(1, id);
			rs = psmt.executeQuery();
			
			if(rs.next()) {
				dto = MemberDTO.builder()
						.id(rs.getInt("id"))
						.name(rs.getString("name"))
						.pass(rs.getString("pass"))
						.regidate(rs.getDate("regidate")).build();
			}
			
		} catch(Exception e) {
			System.out.println("회원 검색 중 오류가 발생하였습니다.");
			e.printStackTrace();
		} finally {
				try {	if(rs != null) rs.close();	} catch (Exception e) {	e.printStackTrace();}
				try {	if(psmt != null) psmt.close();	} catch (Exception e) {	e.printStackTrace();}
		}
		
		return dto; // 존재하지 않는 경우 null을 반환하는데 어떻게 처리해줄지? => DAO에서는 객체(null)를 리턴하고, Controller에서 ResponseEntity를 Return 해주면 된다!!!!!
	}
	
	// 입력
	public MemberDTO postMember(MemberDTO memberDTO) {
		MemberDTO m = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		String query = "insert into member (pass, name) values (?, ?)";
		int cnt = 0;

		try {
			psmt = getCon().prepareStatement(query, Statement.RETURN_GENERATED_KEYS); // RETURN_GENERATED_KEYS
			psmt.setString(1, memberDTO.getPass());
			psmt.setString(2, memberDTO.getName());
			cnt = psmt.executeUpdate();
			
			if(cnt == 1) {
				rs = psmt.getGeneratedKeys(); // 이렇게 가져오면
				rs.next();
				int id = rs.getInt(1);
				System.out.println(id);
				m = getMemberById(id);
			}
			
		} catch(Exception e) {
			System.out.println("데이터 등록 중 오류가 발생하였습니다.");
			e.printStackTrace();
		} finally {
			try {	if(rs != null) rs.close();	} catch (Exception e) {	e.printStackTrace();}
			try {	if(psmt != null) psmt.close();	} catch (Exception e) {	e.printStackTrace();}
		}
		
		return m;
	}
	
	// 수정(객체 교체) - db에서 primary key가 있는데 교체라는게 있을 수 있나...? patch와 다르게 해야할 이유를 못 찾겠다.
	public MemberDTO putMember(Integer id, MemberDTO memberDTO) {
		PreparedStatement psmt = null;
		String query = "update member set pass=?, name=?, regidate=? where id=?";
		int cnt = 0;
		
		try {
			psmt = getCon().prepareStatement(query);
			psmt.setString(1, memberDTO.getPass());
			psmt.setString(2, memberDTO.getName());
			psmt.setDate(3, (Date)memberDTO.getRegidate());
			psmt.setInt(4, id);
			
			cnt = psmt.executeUpdate();
			
		} catch(Exception e) {
			System.out.println("데이터 수정(교체) 중 오류가 발생하였습니다.");
			e.printStackTrace();
		} finally {
			try {	if(psmt != null) psmt.close();	} catch (Exception e) {	e.printStackTrace();}
		}
		
		if(cnt == 0)
			return null;
		else 
			return getMemberById(id);
	}
	
	// 수정(일부 수정)
	public MemberDTO patchMember(Integer id, MemberDTO memberDTO) {
		Statement stmt = null;
		String pass = memberDTO.getPass();
		String name = memberDTO.getName();
		Date regidate = memberDTO.getRegidate();
		int cnt = 0;
		String query = "update member set ";
		
		if(pass != null)
			query += " pass='" + pass + "',";
		if(name != null)
			query += " name='" + name + "',";
		if(regidate != null)
			query += " regidate='" + regidate + "'";
			
		query += " where id=" + id;
		query = query.replace(", where", " where"); // 모두 null이라면 update member set  where id=?가 되는데 그 경우엔 쿼리 실패하면 되니 상관 없음.
		
		try {
			stmt = getCon().createStatement();			
			cnt = stmt.executeUpdate(query);
			
		} catch(Exception e) {
			System.out.println("데이터 수정(일부 수정) 중 오류가 발생하였습니다.");
			e.printStackTrace();
		} finally {
			try {	if(stmt != null) stmt.close();	} catch (Exception e) {	e.printStackTrace();}
		}
		
		if (cnt == 0)
			return null;
		else 
			return getMemberById(id);
	}
	
	// 삭제
	public int deleteMember(Integer id) {
		PreparedStatement psmt = null;
		String query = "delete from member where id=?";
		int cnt = 0;
		
		try {
			psmt = getCon().prepareStatement(query);
			psmt.setInt(1, id);
			cnt = psmt.executeUpdate();
			
		} catch(Exception e) {
			System.out.println("데이터 삭제 중 오류가 발생하였습니다.");
			e.printStackTrace();
		} finally {
			try {	if(psmt != null) psmt.close();	} catch (Exception e) {	e.printStackTrace();}			
		}
		
		return cnt;
	}
	
}