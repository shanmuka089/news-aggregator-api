package com.example.new_aggregator.config.filter;

import com.example.new_aggregator.config.JwtUtils;
import com.example.new_aggregator.models.dto.UserDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class AuthenticationFilter extends OncePerRequestFilter
{
    
    List<String> excludedUrls = List.of(
            "/news-aggregator/api/v1/authenticate",
            "/news-aggregator/api/v1/verify-email",
            "/news-aggregator/api/v1/logout-user",
            "/news-aggregator/api/v1/verify-email"
    );
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
    {
     
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        
        if(token == null || token.isEmpty()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: No token provided");
            return;
        }
        
        UserDto userDto = JwtUtils.decodeToken(token);
        
        if (userDto == null || !JwtUtils.verifyToken(token) || userDto.getExpirationTime() < System.currentTimeMillis()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Invalid token");
            return;
        }

        List<SimpleGrantedAuthority> authorities = userDto.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList());
        UsernamePasswordAuthenticationToken passwordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                userDto.getUsername(),
                null,
                authorities
        );
        
        passwordAuthenticationToken.setDetails(userDto);

        SecurityContextHolder.getContext().setAuthentication(passwordAuthenticationToken);
        
        doFilter(request, response, filterChain);
    }
    
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException
    {
        return excludedUrls.contains(request.getRequestURI()) || 
                (request.getMethod().equalsIgnoreCase("POST") && request.getRequestURI().equals("/news-aggregator/api/v1/users")) ||
                        request.getRequestURI().contains("swagger") || request.getRequestURI().contains("/v3/api-docs/");
    }

}
