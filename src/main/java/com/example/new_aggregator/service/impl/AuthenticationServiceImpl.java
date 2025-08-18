package com.example.new_aggregator.service.impl;

import com.example.new_aggregator.models.dto.UserDto;
import com.example.new_aggregator.service.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthenticationServiceImpl implements AuthenticationService
{
    @Override
    public String loginUser(UserDto userDto)
    {
        return null;
    }

    @Override
    public void logoutUser()
    {

    }
}
