package com.ov.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();

        Authentication result;
        if(isAuthenticated(username, password)){
            logger.info("User '{}' authenticated successfully.", username);
            result = new CustomAuthenticationToken(username, password);
            result.setAuthenticated(true);
        }else {
            throw new BadCredentialsException("Bad credentials");
        }

        return result;
    }

    private boolean isAuthenticated(String username, String password) {
        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);
        return this.passwordEncoder.matches(password, userDetails.getPassword());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(CustomAuthenticationToken.class);
    }
}
