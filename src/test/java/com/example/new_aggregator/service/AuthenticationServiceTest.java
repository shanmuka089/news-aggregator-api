package com.example.new_aggregator.service;

import com.example.new_aggregator.exception.NewsAggregatorErrorCode;
import com.example.new_aggregator.exception.NewsAggregatorException;
import com.example.new_aggregator.models.dto.UserDto;
import com.example.new_aggregator.models.entities.UserEntity;
import com.example.new_aggregator.repository.UserRepository;
import com.example.new_aggregator.service.impl.AuthenticationServiceImpl;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest
{

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    AuthenticationServiceImpl authenticationService;

    @Test
    void testLoginUser() {

        UserDto userDto = new UserDto();
        userDto.setUsername("testUser");
        userDto.setPassword("testPassword");
        userDto.setEmail("test@gmail.com");

        when(userRepository.findByEmail(ArgumentMatchers.anyString())).thenReturn(Optional.of(buildUserEntity()));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);

        String result = authenticationService.loginUser(userDto);
        assert result != null;
    }

    @Test
    void testLoginUserIfPasswordNotValid() {

        UserDto userDto = new UserDto();
        userDto.setUsername("testUser");
        userDto.setPassword("testPassword");
        userDto.setEmail("test@gmail.com");

        when(userRepository.findByEmail(ArgumentMatchers.anyString())).thenReturn(Optional.of(buildUserEntity()));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        assertThrows(NewsAggregatorException.class, () -> authenticationService.loginUser(userDto));
    }

    @Test
    void testLoginUserIfUserIsDisabled() {

        UserDto userDto = new UserDto();
        userDto.setUsername("testUser");
        userDto.setPassword("testPassword");
        userDto.setEmail("test@gmail.com");
        UserEntity user = buildUserEntity();
        user.setDisabled(true);

        when(userRepository.findByEmail(ArgumentMatchers.anyString())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);

        assertThrows(NewsAggregatorException.class, () -> authenticationService.loginUser(userDto));
    }

    @Test
    void testLoginUserIfUSerNotValid() {

        UserDto userDto = new UserDto();
        userDto.setUsername("testUser");
        userDto.setPassword("testPassword");
        userDto.setEmail("test@gmail.com");

        when(userRepository.findByEmail(ArgumentMatchers.anyString())).thenReturn(Optional.empty());

        assertThrows(NewsAggregatorException.class, () -> authenticationService.loginUser(userDto));
    }

    @Test
    void testVerifyEmail() {
        when(userRepository.findById(anyLong())).thenReturn(java.util.Optional.of(buildUserEntity()));
        when(userRepository.save(ArgumentMatchers.any(UserEntity.class))).thenReturn(buildUserEntity());
        
        authenticationService.verifyEmail(1L);
    }

    UserEntity buildUserEntity() {

        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(1L);
        userEntity.setUsername("testUser");
        userEntity.setDisabled(false);
        userEntity.setLocked(false);
        userEntity.setPassword("encodedPassword");
        return userEntity;
    }

}
