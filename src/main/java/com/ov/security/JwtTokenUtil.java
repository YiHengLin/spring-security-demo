package com.ov.security;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil implements Serializable {
	
	private static final long serialVersionUID = 679402599437128519L;
	
	@Value("${jwt.expirationHour}")
    private int expirationHour;
	
	@Value("${jwt.secretKey}")
	private String secretKey;

	public String genetateToken(UserDetails userDetails) {
		String subject = userDetails.getUsername(); 
		
		Calendar cal = Calendar.getInstance();
		final Date createdDate = cal.getTime();
		cal.add(Calendar.HOUR_OF_DAY, expirationHour); // 
		final Date expiredDate = cal.getTime();
		
		Map<String, Object> claims = new HashMap<>();
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(subject)
				.setIssuedAt(createdDate)
				.setExpiration(expiredDate)
				.signWith(SignatureAlgorithm.HS512, secretKey)
				.compact();
	}

}
