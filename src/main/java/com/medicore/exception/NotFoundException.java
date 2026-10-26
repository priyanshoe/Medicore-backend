package com.medicore.exception;

public class NotFoundException extends RuntimeException {

	public NotFoundException(String item) {
		super(item+" not found");
	}
}
