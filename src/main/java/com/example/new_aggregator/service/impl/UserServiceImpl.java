package com.example.new_aggregator.service.impl;

import com.example.new_aggregator.models.dto.UserDto;
import com.example.new_aggregator.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService
{
    

    @Override
    public UserDto saveUser(UserDto userDto) {
        System.out.println("User Service invoked");
        return userDto;
    }

    @Override
    public void loginUser(UserDto userDto)
    {
        
    }

    @Override
    public void logoutUser()
    {

    }

    @Override
    public UserDto fetchUser()
    {

        return null;
    }

}
