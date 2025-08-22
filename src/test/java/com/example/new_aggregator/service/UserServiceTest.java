package com.example.new_aggregator.service;

import com.example.new_aggregator.models.dto.UserDto;
import com.example.new_aggregator.models.entities.UserEntity;
import com.example.new_aggregator.repository.UserRepository;
import com.example.new_aggregator.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest
{

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    
    @InjectMocks
    UserServiceImpl userService;
    
    @Test
    void saveUser() {

        UserDto userDto = new UserDto();
        userDto.setUsername("testUser");
        userDto.setPassword("testPassword");
        userDto.setEmail("test@gmail.com");
        
        when(userRepository.save(ArgumentMatchers.any())).thenReturn(buildUserEntity());
        when(passwordEncoder.encode("testPassword")).thenReturn("encodedPassword");
        
        UserDto savedUser = userService.saveUser(userDto);
        assert savedUser != null;
        assert savedUser.getUsername().equals("testUser");
        assert savedUser.getPassword() == null;
    }
    
    @Test
    void fetchUser() {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken("testUser", "password");
        UserDto userDto = new UserDto();
        userDto.setUserId(1L);
        userDto.setUsername("testUser");
        authentication.setDetails(userDto);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(buildUserEntity()));
        
        UserDto fetchedUser = userService.fetchUser();
        assert fetchedUser != null;
        assert fetchedUser.getUsername().equals("testUser");
        assert fetchedUser.getPassword() == null;
        SecurityContextHolder.clearContext();
    }
    
    UserEntity buildUserEntity() {
        
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(1L);
        userEntity.setUsername("testUser");
        userEntity.setDisabled(true);
        userEntity.setLocked(false);
        userEntity.setPassword("encodedPassword");
        return userEntity;
    }
    
    
    
    
}
