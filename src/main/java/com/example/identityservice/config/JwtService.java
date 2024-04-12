package com.example.identityservice.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@Slf4j
public class JwtService {

    @Value("${pride.jwt-secret}")
    private String secretKey;

    @Value("${pride.jwt-token-validity}")
    private int tokenValidity;

    final String SECRET_KEY= "2A0EeVEO6BDBUJRL2c3MGulyLbXajOGIwp+U2NqvL0nA1aOI0zgPZdu/bLdjNUXl";
    public String extractUserName(String jwt) {
        return extractClaim(jwt, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token).getBody();
    }

    public String generateToken(UserDetailsImpl userDetails) {
        return generateToken(new HashMap<>(), userDetails.getUser().getEmail());
    }

    public boolean isTokenIsValid(String token) {
        try {
            Date expiration = extractClaim(token, Claims::getExpiration);
            return !expiration.before(new Date());
        } catch (ExpiredJwtException e) {
            return false;
        }
    }


    public String generateToken(Map<String, Object> claims, String subject){
        long issuedAt = System.currentTimeMillis();
        String token= Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(issuedAt))
                .setExpiration(new Date(System.currentTimeMillis() + tokenValidity))
                .signWith(SignatureAlgorithm.HS256, secretKey).compact();

        log.debug("Generated token: {}", token);

        return token;

    }
}
