package com.example.new_aggregator.service;

import com.example.new_aggregator.models.dto.UserDto;

public interface UserService
{

    UserDto saveUser(UserDto userDto);
    void loginUser(UserDto userDto);
    void logoutUser();

    UserDto fetchUser();

}
