package com.rubypaper;

import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j // 이 Lombok Annotation은 private Logger log = LoggerFactory.getLogger(LoggingRunner.class);을 대체한다.
@Service
public class LoggingRunner implements ApplicationRunner {// ApplicationRunner는 애플리케이션이 시작할 때
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		log.trace("Trace 로그 메시지"); // 여기서 log는 @Slf4j 로 자동으로 
		log.debug("Debug 로그 메시지");
		log.info("Info 로그 메시지"); // 스프링 부터는 기본적으로 로그 레벨을 INFO로 처리하는데, 로그 레벨을 변경하고 싶으면 프로퍼티 파일을 수정한다.
		log.warn("Warn 로그 메시지");
		log.error("Error 로그 메시지");
	}
	
}
