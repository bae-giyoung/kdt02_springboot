package common.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class JDBConnect {
	private Connection con;
	
	// 기본 생성자
	public JDBConnect() {
		try {
			// JDBC 드리이버 로드
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// DB에 연결
			String url = "jdbc:mysql://localhost:3306/bootmission";
			String id = "musthave";
			String pwd = "tiger";
			con  = DriverManager.getConnection(url, id, pwd);
			
			System.out.println("DB에 연결 성공(기본 생성자)");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// 생성자 오버로딩
	public JDBConnect(String driver, String url, String id, String pwd) {
		try {
			// JDBC 드리이버 로드
			Class.forName(driver);
			
			// DB에 연결
			con  = DriverManager.getConnection(url, id, pwd);
			
			System.out.println("DB에 연결 성공(인수 생성자 1)");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public Connection getCon() {
		return con;
	}
	
	// 연결 해제(자원 반납)
	public void close() {
		try {
			if (con != null) con.close();
			System.out.println("JDBC 자원 해제");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}