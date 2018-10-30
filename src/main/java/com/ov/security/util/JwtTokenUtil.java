package com.ov.security.util;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil implements Serializable {
	
	private static final long serialVersionUID = 679402599437128519L;
	
	@Value("${jwt.expirationHour}")
    private int expirationHour;
	
	@Value("${jwt.secretKey}")
	private String secretKey;

	public String generateToken(UserDetails userDetails) {
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

	public String getUsernameFromToken(String token) {
		
		final Claims claims = Jwts.parser()
				.setSigningKey(secretKey)
				.parseClaimsJws(token)
				.getBody();
		
		return claims.getSubject();
	}
	
	private Date getExpirationDateFromToken (String token) {
		final Claims claims = Jwts.parser()
				.setSigningKey(secretKey)
				.parseClaimsJws(token)
				.getBody();	
		return claims.getExpiration();

	}
	
	private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        Calendar cal = Calendar.getInstance();
        return expiration.before(cal.getTime());
    }
	

	public boolean checkTokenExpired(String jwtToken, String username) {
		String jwtUsername = this.getUsernameFromToken(jwtToken);
		return (!this.isTokenExpired(jwtToken)) && jwtUsername.equals(username);
	}

}
