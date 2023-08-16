package com.coderscampus.flightTrack.service;

import java.security.Key;
import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

public class JwtService {
	
	@Value("${jwt.signingKey}")
	private String jwtSigningKey;
	@Value("${jwt.expi}")
	private Long expirationTimeInMillis;
	
	public String generateToken(Map<String, Object>extraClaims,UserDetails user) {
		String jwt = Jwts.builder()
		    .setClaims(extraClaims)
		    .setSubject(user.getUsername())
		    .setIssuedAt(new Date())
		    .setExpiration(new Date(System.currentTimeMillis()+ expirationTimeInMillis))
		    .signWith(null,SignatureAlgorithm.HS256)
		    .compact();
		return jwt;
	}

	private Key getSigningKey() {
		byte[] jwtSigningKeyAsBytes=Decoders.BASE64.decode(jwtSigningKey);
		SecretKey secretKey = Keys.hmacShaKeyFor(jwtSigningKeyAsBytes);
		return secretKey;
	}
}
