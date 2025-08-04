package com.rubypaper.jdbc.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rubypaper.jdbc.util.JDBCConnectionManager;
 
@Configuration
@EnableConfigurationProperties(JDBCConnectionManagerProperties.class)
public class BoardAutoConfiguration {
	 @Autowired
	 private JDBCConnectionManagerProperties properties;
	 
	 @Bean // return 타입 객체를 IoC Container에 넣어줘라!
	 @ConditionalOnMissingBean // 사용자가 설정한 Bean이 먼저 등록되는데 등록된게 없으면 등록
	 JDBCConnectionManager getJDBCConnectionManager() {
		 JDBCConnectionManager manager = new JDBCConnectionManager();
		 manager.setDriverClass(properties.getDriverClass());
		 manager.setUrl(properties.getUrl());
		 manager.setUsername(properties.getUsername());
		 manager.setPassword(properties.getPassword());
		 return manager;
	 }

}