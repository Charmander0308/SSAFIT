package com.ssafit.global.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	 
	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	 
	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ErrorResponse> handleCustomException(CustomException e, HttpServletRequest request){
		log.warn("[{}] CustomException 발생 : {} - {}", request.getRequestURI(), e.getErrorCode().name(), e.getMessage());
		
		return ResponseEntity
				.status(e.getErrorCode().getStatus())
				.body(new ErrorResponse(e.getErrorCode()));
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleException(Exception e, HttpServletRequest request){
		log.error("[{}] 예상치 못한 에러 발생 : {}", request.getRequestURI(), e.getMessage(), e);
		
		return ResponseEntity
				.status(ErrorCode.INTERNAL_SERVER_ERROR.getStatus())
				.body(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR));
	}
	
}
