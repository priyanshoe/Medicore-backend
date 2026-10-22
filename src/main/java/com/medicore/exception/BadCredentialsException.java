package com.medicore.exception;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.AuthenticationException;

public class BadCredentialsException extends AuthenticationException {
	
	public BadCredentialsException() {
		super("Bad Credentials");
	}

}
