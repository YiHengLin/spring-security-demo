package com.ov.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ov.model.security.User;
import com.ov.security.repository.UserRepository;


/**
 * @author Henry.Lin
 * @see https://docs.spring.io/spring-security/site/docs/5.0.2.RELEASE/reference/htmlsingle/#jc-authentication-userdetailsservice
 *
 */
@Service
public class JwtUserDetailsService implements UserDetailsService {
	
	
	@Autowired
	private UserRepository userRepository; 

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		
		if (user == null ) {
			throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
		} else {
			return JwtUserFactory.create(user);
		}		
	}
}
