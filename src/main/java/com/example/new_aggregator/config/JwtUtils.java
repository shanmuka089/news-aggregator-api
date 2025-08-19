package com.example.new_aggregator.config;

import com.example.new_aggregator.models.dto.UserDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JwtUtils
{
    @Autowired
    private String SECRET_KEY;

    public String encodeToken(UserDto userDto) {
        String token = Jwts.builder()
                .subject(userDto.getUsername())
                .claim("userId", userDto.getUserId())
                .claim("email", userDto.getEmail())
                .claim("roles", userDto.getRoles())
                .claim("expiration", System.currentTimeMillis() + 3600000)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
        
        return token;
    }
    
    public boolean verifyToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return true;
        } catch(Exception exception) {
            return false;
        }
    }
    
    public UserDto decodeToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        
        UserDto userDto = new UserDto();
        userDto.setUsername(claims.getSubject());
        userDto.setEmail(claims.get("email", String.class));
        userDto.setRoles(claims.get("roles", List.class));
        userDto.setUserId(claims.get("userId", Long.class));
        
        return userDto;
    }
}
