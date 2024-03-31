package com.lcwd.hotel.services.exception;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExccepionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Map<String, Object>> handlerResourceNotFoundException(ResourceNotFoundException exception ){
		Map<String, Object> responce=	Map.of("message",exception.getMessage(),"success", true , "httpStatus",HttpStatus.NOT_FOUND);
		return new ResponseEntity<Map<String,Object>>(responce,HttpStatus.NOT_FOUND);
	}
	
}
