package com.example.new_aggregator.service.impl;

import com.example.new_aggregator.exception.NewsAggregatorErrorCode;
import com.example.new_aggregator.exception.NewsAggregatorException;
import com.example.new_aggregator.mapper.UserDtoMapper;
import com.example.new_aggregator.models.dto.UserDto;
import com.example.new_aggregator.models.entities.RoleEntity;
import com.example.new_aggregator.models.entities.UserEntity;
import com.example.new_aggregator.repository.UserRepository;
import com.example.new_aggregator.service.UserService;
import com.example.new_aggregator.utils.Constants;
import com.example.new_aggregator.utils.NewsAggregatorUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService
{
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    
    @Override
    public UserDto saveUser(UserDto userDto) {

        UserEntity userEntity = UserDtoMapper.INSTANCE.toEntity(userDto);
        userEntity.setDisabled(true);
        userEntity.setLocked(false);
        userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));

        RoleEntity userRole = new RoleEntity();
        userRole.setRoleName("USER");
        userRole.getUsers().add(userEntity);

        RoleEntity adminROle = new RoleEntity();
        adminROle.setRoleName("ADMIN");
        adminROle.getUsers().add(userEntity);
        
        userEntity.getRoles().add(userRole);
        userEntity.getRoles().add(adminROle);
        
        UserEntity user = userRepository.save(userEntity);
        
        UserDto userResponse = UserDtoMapper.INSTANCE.toDto(user);
        return userResponse;
    }

    @Override
    public UserDto fetchUser()
    {

        Long userId = NewsAggregatorUtils.fetchCurrentUser().getUserId();
        
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new NewsAggregatorException(NewsAggregatorErrorCode.USER_NOT_FOUND));
        UserDto userDto = UserDtoMapper.INSTANCE.toDto(user);
        
        return userDto;
    }

}
