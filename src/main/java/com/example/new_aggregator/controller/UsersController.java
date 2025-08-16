package com.example.new_aggregator.controller;

import com.example.new_aggregator.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("${spring.application.base-path}")
public class UsersController
{
    @Autowired
    private UserService userService;
    
    @RequestMapping("/test")
    public String getUsers()
    {
        userService.test();
        return "Test Successful";
    }
}
