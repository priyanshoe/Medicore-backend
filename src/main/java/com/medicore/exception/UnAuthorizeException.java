package com.medicore.exception;

public class UnAuthorizeException extends RuntimeException {

	public UnAuthorizeException() {
		super("Unauthorized request");
	}
	
}
