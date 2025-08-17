package com.example.new_aggregator.controller;

import com.example.new_aggregator.config.annotations.ApiResponseDto;
import com.example.new_aggregator.models.dto.UserDto;
import com.example.new_aggregator.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("${spring.application.base-path}")
public class UsersController
{
    @Autowired
    private UserService userService;
    
    @PostMapping("/users/register")
    public String registerUser(@RequestBody UserDto userDto)
    {
        userService.saveUser(userDto);
        return "Test Successful";
    }
    
    @GetMapping("/users")
    public String fetchAuthenticatedUser()
    {
        userService.fetchUser();
        return "Test Successful";
    }
    
    @PostMapping("/users/login")
    public String loginUser(@RequestBody UserDto userDto)
    {
        userService.loginUser(userDto);
        return "Login Successful";
    }
    
    @PostMapping("/users/logout")
    public String logoutUser()
    {

        userService.logoutUser();
        return "Logout Successful";
    }
}
