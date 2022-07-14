package org.zzz.jt.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
//@ResponseStatus(value = HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS)
public class ApiException extends RuntimeException{

	public ApiException(String message) {
		super(message);
	}
	
	

}
