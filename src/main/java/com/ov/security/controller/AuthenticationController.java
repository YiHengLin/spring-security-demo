package com.ov.security.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ov.security.entity.AuthenticationException;
import com.ov.security.entity.AuthenticationRequest;
import com.ov.security.entity.AuthenticationResponse;
import com.ov.security.util.JwtTokenUtil;

@RestController
public class AuthenticationController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	@Qualifier("jwtUserDetailsService")
	private UserDetailsService userDetailsService;
	

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	
	@RequestMapping(value = "${jwt.route.authentication}", 
			method = RequestMethod.POST,
			consumes = {MediaType.APPLICATION_JSON_VALUE}
	)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws AuthenticationException {
		
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		
		final String token = jwtTokenUtil.genetateToken(userDetails);
        
		return ResponseEntity.ok(new AuthenticationResponse(token));
		
	}
	
//	@ExceptionHandler({AuthenticationException.class})
//	public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
//		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
//	}
	
	
	private void authenticate(String username, String password) {
		Objects.requireNonNull(username);
        Objects.requireNonNull(password);
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new AuthenticationException("User is disabled!", e);
        } catch (BadCredentialsException e) {
            throw new AuthenticationException("Bad credentials!", e);
        }		
	}

}
