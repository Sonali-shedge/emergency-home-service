package com.EventManagementSystem.utils;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.EventManagementSystem.entity.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {
	

	   

	    private static final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hour

	 

	    private Key getSignKey() {
	        String secret = "my_super_secret_key_which_is_long_enough"; // 256-bit recommended
	        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
	        return Keys.hmacShaKeyFor(keyBytes);
	    }
//	    public String generateToken(User user) {
//
//	        return Jwts.builder()
//	                .setSubject(user.getEmail())   // 🔥 REQUIRED
//	                .setIssuedAt(new Date())
//	                .setExpiration(
//	                    new Date(System.currentTimeMillis() + 1000 * 60 * 60)
//	                )
//	                .signWith(getSignKey())
//	                .compact();
//	    }

	    
	    public String generateToken(User user) {

	        Map<String, Object> claims = new HashMap<>();

	        claims.put(
	            "roles",
	            user.getAuthorities()
	                .stream()
	                .map(GrantedAuthority::getAuthority)
	                .toList()
	        );

	        return Jwts.builder()
	                .setClaims(claims)
	                .setSubject(user.getEmail())
//	                .setSubject(String.valueOf(user.getUserId()))
	                .setIssuedAt(new Date(System.currentTimeMillis()))
	                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
	                .signWith(getSignKey())
	                .compact();
	    }



	    public String extractUsername(String token) {
	        return Jwts.parserBuilder()
	                .setSigningKey(getSignKey())
	                .build()
	                .parseClaimsJws(token)
	                .getBody()
	                .getSubject();
	    }
	    
	    public boolean isTokenValid(String token, UserDetails userDetails) {

	        final String username = extractUsername(token);
	        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	    }

	    private boolean isTokenExpired(String token) {

	        Date expiration = Jwts.parserBuilder()
	                .setSigningKey(getSignKey())
	                .build()
	                .parseClaimsJws(token)
	                .getBody()
	                .getExpiration();

	        return expiration.before(new Date());
	    }


	}

	


