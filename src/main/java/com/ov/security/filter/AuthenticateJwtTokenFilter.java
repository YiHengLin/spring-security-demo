package com.ov.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ov.security.JwtUserDetails;
import com.ov.security.util.JwtTokenUtil;

import io.jsonwebtoken.ExpiredJwtException;

public class AuthenticateJwtTokenFilter extends OncePerRequestFilter {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final UserDetailsService userDetailsService;

	private final JwtTokenUtil jwtTokenUtil;

	public AuthenticateJwtTokenFilter(UserDetailsService userDetailsService, JwtTokenUtil jwtTokenUtil) {
		this.userDetailsService = userDetailsService;
		this.jwtTokenUtil = jwtTokenUtil;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		
		final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);	
		
		String jwtToken = null;
		String username = null;
		
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			jwtToken = authorizationHeader.substring(7);			
			try {
				username = jwtTokenUtil.getUsernameFromToken(jwtToken);				
			} catch (IllegalArgumentException e) {
				logger.error("an error occurred when getting username from jwtToken!", e);
				filterChain.doFilter(request, response);
			} catch (ExpiredJwtException e) {
				logger.warn("jwtToken is expired!", e);
			}
		} else {
			logger.warn("No Bearer header found!");
		}
		
		logger.debug("checking authentication with username: '{}'", username);
		
		if(username != null) {		
			try {
				JwtUserDetails userDetails = (JwtUserDetails) this.userDetailsService.loadUserByUsername(username);	
				
				// Validate username (compare with DB) and expiration stored JWT token  
				if(jwtTokenUtil.checkTokenExpired(jwtToken, userDetails.getUsername())) {
					logger.info("User with username: '{}' is authenticated, updating security context", username);
					Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					SecurityContextHolder.getContext().setAuthentication(authentication);			
				}
			} catch (UsernameNotFoundException e) {
				logger.warn("Invalid username in JwtToken");
			}	
		}
		filterChain.doFilter(request, response);
	}
}
