package com.ecommerce.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalHandlerException {

	@ExceptionHandler(ResourceNotFoundException.class)
	public String HandelResourceNotFoundException(ResourceNotFoundException ex) {
		return ex.getMessage();
	}
}
