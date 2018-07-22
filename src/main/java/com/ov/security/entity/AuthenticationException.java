package com.ov.security.entity;

public class AuthenticationException extends RuntimeException {
	
	public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
