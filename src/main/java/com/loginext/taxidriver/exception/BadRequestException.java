package com.loginext.taxidriver.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

    private static final long serialVersionUID = 4025856187797199395L;

	public BadRequestException(String message) {
		super(message);
	}
	
	public BadRequestException(String message, Throwable t){
		super(message, t);
	}
}
