package com.ov.security.entity;

import java.io.Serializable;

public class AuthenticationResponse implements Serializable {
	

	private static final long serialVersionUID = 8387229123331579310L;
	
	private final String token;
	
	public AuthenticationResponse(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}
}
