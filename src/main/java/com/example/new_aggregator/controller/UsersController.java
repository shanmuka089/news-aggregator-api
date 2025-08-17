package com.example.new_aggregator.controller;

import com.example.new_aggregator.config.ApiDocsConfig;
import com.example.new_aggregator.models.dto.UserDto;
import com.example.new_aggregator.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "Users")
@RestController
@RequestMapping("${spring.application.base-path}")
public class UsersController
{
    @Autowired
    private UserService userService;

    @ApiDocsConfig
    @PostMapping("/users/register")
    public String registerUser(@RequestBody UserDto userDto)
    {
        userService.test();
        return "Test Successful";
    }

    @ApiDocsConfig
    @PostMapping("/users/login")
    public String loginUser(@RequestBody UserDto userDto)
    {
        userService.test();
        return "Login Successful";
    }
    
    @ApiDocsConfig
    @PostMapping("/users/logout")
    public String logoutUser()
    {

        userService.test();
        return "Logout Successful";
    }
}
