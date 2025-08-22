package com.example.new_aggregator;

import com.example.new_aggregator.models.dto.RoleDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeAll;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestUtils
{

    private static String TEST_SECRET_KEY = "DIOFHUIWHKNCSAILHOHLXCSNJNCBDVJKGKLDbNKRMNLSADSNKJEBEWHKJEBVKNLEWCDBVFKJ";
    
    public static String generateToken() {
        
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", "testUser");
        claims.put("roles", List.of(new RoleDto("ROLE_USER")));
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(SignatureAlgorithm.HS256, TEST_SECRET_KEY)
                .compact();
    }

}
