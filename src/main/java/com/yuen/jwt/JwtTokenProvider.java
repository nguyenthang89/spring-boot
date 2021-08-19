package com.yuen.jwt;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.yuen.domain.web.MyUserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtTokenProvider {
	
	private final String JWT_SECRET = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCeyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZ";

	// Set time 30 second
	private final long JWT_EXPIRATION = 300000L;

	public String generateToken(MyUserDetails userDetails) {
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
		// Generate token from Email
		return Jwts.builder().setSubject(userDetails.getUser().getEmail()).setIssuedAt(now).setExpiration(expiryDate)
				.signWith(SignatureAlgorithm.HS512, JWT_SECRET).compact();
	}

	public String getEmailFromJWT(String token) {
		Claims claims = Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody();
		return claims.getSubject();
	}

	public boolean validateToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken);
			return true;
		} catch (MalformedJwtException ex) {
			log.error("Invalid JWT token");
		} catch (ExpiredJwtException ex) {
			log.error("Expired JWT token");
		} catch (UnsupportedJwtException ex) {
			log.error("Unsupported JWT token");
		} catch (IllegalArgumentException ex) {
			log.error("JWT claims string is empty.");
		}
		return false;
	}
}
