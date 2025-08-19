package com.example.new_aggregator.controller;

import com.example.new_aggregator.models.dto.ResponseDto;
import com.example.new_aggregator.models.dto.UserDto;
import com.example.new_aggregator.service.AuthenticationService;
import com.example.new_aggregator.service.UserService;
import com.example.new_aggregator.utils.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${spring.application.base-path}")
public class UsersController
{
    @Autowired
    private UserService userService;
    
    
    /**
     * Registers a new user.
     * @param userDto UserDto containing user details to be registered.
     * @return ResponseEntity containing ResponseDto with UserDto and HTTP status CREATED.
     */
    @PostMapping("/users")
    public ResponseEntity<ResponseDto<UserDto>> registerUser(@RequestBody UserDto userDto)
    {
        UserDto user = userService.saveUser(userDto);
        return new ResponseEntity<>(new ResponseDto<>(ResponseStatus.SUCCESS, user),HttpStatus.CREATED);
    }
    
    /**
     * Fetches the authenticated user's details.
     * @return ResponseEntity containing ResponseDto with UserDto and HTTP status OK.
     */
    @GetMapping("/users")
    public ResponseEntity<ResponseDto<UserDto>> fetchAuthenticatedUser()
    {
        UserDto user = userService.fetchUser();
        return new ResponseEntity<>(new ResponseDto<>(ResponseStatus.SUCCESS, user),HttpStatus.OK);
    }
}
