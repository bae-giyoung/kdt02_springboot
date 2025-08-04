package edu.pnu.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

import common.util.JDBConnect;

public class LogDao extends JDBConnect {
	public LogDao() {
		super("com.mysql.cj.jdbc.Driver", "jdbc:mysql://localhost:3306/bootmission", "musthave", "tiger");
	}
	
	public void addLog(Map<String, Object> map) {
		/* [dblog]
		 * id int auto_increment primary key,
		 * method varchar(10) not null,
		 * sqlstring varchar(256) not null,
		 * regidate date default(curdate()) not null,
		 * success boolean default true
		 */
		PreparedStatement psmt = null;
		String sql = "insert into dblog (method, sqlstring, success) values (?, ?, ?)";
		String method = map.get("method").toString();
		String sqlstring = map.get("sqlstring").toString();
		Boolean success = (Boolean)map.get("success");
		int cnt = 0;
		
		try {
			psmt = getCon().prepareStatement(sql);
			psmt.setString(1, method);
			psmt.setString(2, sqlstring);
			psmt.setBoolean(3, success);
			
			cnt = psmt.executeUpdate();
			
			if(cnt == 0) 
				System.out.println("로그 저장 실패");
			else 
				System.out.println("로그 저장 성공");
			
		} catch (Exception e) {
			System.out.println("로그 저장 중 오류 발생");
			e.printStackTrace();
		} finally {
				try {
					if(psmt != null) psmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}
}
