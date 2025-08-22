package com.example.new_aggregator.config;

import com.example.new_aggregator.models.dto.RoleDto;
import com.example.new_aggregator.models.dto.UserDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JwtUtils
{
    private static ObjectMapper objectMapper = new ObjectMapper();
    private static final String SECRET_KEY = "DIOFHUIWHKNCSAILHOHLXCSNJNCBDVJKGKLDbNKRMNLSADSNKJEBEWHKJEBVKNLEWCDBVFKJ";

    public static String encodeToken(UserDto userDto) {
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
    
    public static boolean verifyToken(String token) {
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
    
    public static UserDto decodeToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        
        UserDto userDto = new UserDto();
        userDto.setUsername(claims.getSubject());
        userDto.setEmail(claims.get("email", String.class));
        userDto.setUserId(claims.get("userId", Long.class));
        userDto.setExpirationTime(claims.get("expiration", Long.class));
        
        List<RoleDto> roles = objectMapper.convertValue(claims.get("roles"), new TypeReference<List<RoleDto>>() {});
        
        userDto.setRoles(roles);
        
        return userDto;
    }
}
