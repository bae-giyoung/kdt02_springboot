package edu.pnu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.rubypaper.jdbc.util.JDBCConnectionManager;

@Component
public class JDBCConnectionManagerRunner implements ApplicationRunner {
	
	@Autowired
	private JDBCConnectionManager connectionManager;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
			System.out.println("커넥션매니저: "+ connectionManager.toString());
	}
}