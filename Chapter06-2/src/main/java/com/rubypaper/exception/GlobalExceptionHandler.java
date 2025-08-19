package com.rubypaper.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	// @ExceptionHandler: BoardException.class와 그를 상속한 모든 클래스에서 발생한 오류를 이 핸들러에서 처리하겠다!
	// 가장 좁은 예외를 위에 두고
	// 가장 넓은 예외를 뒤에 둬야한다!
	@ExceptionHandler(BoardException.class)
	public String handleCustomException(BoardException exception, Model model) {
		model.addAttribute("exception", exception);
		return "/errors/boardError";
	}
	
	@ExceptionHandler(Exception.class)
	public String handleException(Exception exception, Model model) {
		model.addAttribute("exceptionMessage", exception.getMessage());
		model.addAttribute("stackTrace", exception.getStackTrace());
		return "/errors/globalError";
	}
	
	
}
