package edu.pnu;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rubypaper.jdbc.util.JDBCConnectionManager;

//@Configuration
public class BoardConfiguration {
//	@Bean
	JDBCConnectionManager getJDBCConnectionManager() {
		JDBCConnectionManager manager = new JDBCConnectionManager();
		manager.setDriverClass("org.h2.Driver");
		manager.setUrl("jdbc:h2:tcp://localhost/~/test");
		manager.setUsername("sa");
		manager.setPassword("");
		return manager;
	}
}

/*
 * [사용자설정객체와 자동설정객체가 충돌남]
***************************
APPLICATION FAILED TO START
***************************

Description:

The bean 'getJDBCConnectionManager', defined in class path resource [com/rubypaper/jdbc/config/BoardAutoConfiguration.class], could not be registered. A bean with that name has already been defined in class path resource [edu/pnu/BoardConfiguration.class] and overriding is disabled.

Action:

Consider renaming one of the beans or enabling overriding by setting spring.main.allow-bean-definition-overriding=true
*/