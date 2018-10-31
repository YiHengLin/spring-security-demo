package com.ov.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;


public class CustomAuthenticationToken extends AbstractAuthenticationToken {

    private final Object principal;

    private Object credentials;

    public CustomAuthenticationToken(Object principal, Object credentials) {
        super(null);
        this.principal = principal;
        this.credentials = credentials;
        super.setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
}
