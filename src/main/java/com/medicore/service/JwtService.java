package com.medicore.service;


import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.medicore.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	private final SecretKey key;
	private final long EXPIRATION_TIME = 60 * 60 * 1000L; // 1 Hour
	
	public JwtService(@Value("${jwt.secret}") String key) {
		this.key = Keys.hmacShaKeyFor(
				key.getBytes(StandardCharsets.UTF_8));
	}
	
	public String genrateToken(User user) {
		return Jwts.builder()
				.subject(user.getId().toString())
				.claim("email", user.getEmail())
				.claim("role", user.getRole())
				.signWith(key)
				.issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.compact();

	}
	
	
	public Claims extractAllClaims(String token) {
		return Jwts.parser()
				.verifyWith(key)
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}
	
	public String extractUserId(String token) {
		return extractAllClaims(token).getSubject();
	}
	
	public String extractUserRole(String token){
		return extractAllClaims(token).get("role", String.class);
	}
	
	public String extractUserEmail(String token){
		return extractAllClaims(token).get("email", String.class);
	}
	
	public boolean isTokenValid(String token, UserDetails user) {
		String userEmail = extractUserEmail(token);
		return userEmail.equals(user.getUsername()) && !isTokenExpired(token);
	}
	
	public boolean isTokenExpired(String token) {
		return extractAllClaims(token).getExpiration().before(new Date());
	}
	
}
