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
    
    @Autowired
    private AuthenticationService authenticationService;
    
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
    
    /**
     * Updates the authenticated user's details.
     * @param userDto UserDto containing updated user details.
     * @return ResponseEntity containing ResponseDto with UserDto and HTTP status OK.
     */
    @PostMapping("/users/login")
    public ResponseEntity<ResponseDto<String>> loginUser(@RequestBody UserDto userDto)
    {
        String token = authenticationService.loginUser(userDto);
        return new ResponseEntity<>(new ResponseDto<>(ResponseStatus.SUCCESS, token), HttpStatus.OK);
    }
    
    /**
     * Logs out the authenticated user.
     * @return ResponseEntity containing ResponseDto with success message and HTTP status OK.
     */
    @PostMapping("/users/logout")
    public ResponseEntity<ResponseDto<String>> logoutUser()
    {
        authenticationService.logoutUser();
        return new ResponseEntity<>(new ResponseDto<>(ResponseStatus.SUCCESS, "User logged out successfully"), HttpStatus.OK);
    }
}
