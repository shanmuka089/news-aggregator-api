package com.example.new_aggregator.service.impl;

import com.example.new_aggregator.config.JwtUtils;
import com.example.new_aggregator.exception.NewsAggregatorErrorCode;
import com.example.new_aggregator.exception.NewsAggregatorException;
import com.example.new_aggregator.mapper.UserDtoMapper;
import com.example.new_aggregator.models.dto.RoleDto;
import com.example.new_aggregator.models.dto.UserDto;
import com.example.new_aggregator.models.entities.RoleEntity;
import com.example.new_aggregator.models.entities.UserEntity;
import com.example.new_aggregator.repository.UserRepository;
import com.example.new_aggregator.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AuthenticationServiceImpl implements AuthenticationService
{
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    

    @Override
    public String loginUser(UserDto userDto)
    {
         UserEntity userEntity = userRepository.findByEmail(userDto.getEmail()).orElseThrow(() -> new NewsAggregatorException(NewsAggregatorErrorCode.INVALID_USER_OR_PASSWORD));
         
         boolean isPasswordValid = passwordEncoder.matches(userDto.getPassword(), userEntity.getPassword());
         
         if(!isPasswordValid || userEntity.isDisabled()) {
             throw new NewsAggregatorException(NewsAggregatorErrorCode.INVALID_USER_OR_PASSWORD);
         }
         
         userDto.setUserId(userEntity.getUserId());
         List<RoleDto> roles = userEntity.getRoles().stream().map(RoleEntity ::getRoleName).map(roleName -> new RoleDto(roleName)).collect(Collectors.toList());
         userDto.setRoles(roles);
         userDto.setUsername(userEntity.getUsername());
         
        return JwtUtils.encodeToken(userDto);
    }
    

    @Override
    public void verifyEmail(Long userId)
    {
        userRepository.findById(userId).ifPresentOrElse(userEntity -> {
            userEntity.setDisabled(false);
            userRepository.save(userEntity);
        }, () -> {
            throw new NewsAggregatorException(NewsAggregatorErrorCode.USER_NOT_FOUND);
        });
    }

    @Override
    public void logoutUser()
    {
        
    }
}
