package com.ov.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ov.model.security.User;
import com.ov.security.repository.UserRepository;


/**
 * Custom authentication
 */
@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository; 

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		
		if (user == null ) {
			throw new UsernameNotFoundException(String.format("No user found with username: %s.", username));
		} else {
			return JwtUserFactory.create(user);
		}		
	}
}
