package com.medicore.exception;

public class ConflictException extends RuntimeException {

	public ConflictException(String item) {
		super(item+" already exist");
	}
}
