package com.ov.security.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ov.security.JwtUserDetails;
import com.ov.security.util.JwtTokenUtil;

@RestController
public class UserController {
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@RequestMapping(value = "user", method = RequestMethod.GET)
    public JwtUserDetails getAuthenticatedUser(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION).substring(7); // Authorization:Bearer xxx
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUserDetails user = (JwtUserDetails) userDetailsService.loadUserByUsername(username);
        return user;
    }

}
