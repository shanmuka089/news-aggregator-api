package com.example.new_aggregator.controller;

import com.example.new_aggregator.models.dto.ResponseDto;
import com.example.new_aggregator.models.dto.UserDto;
import com.example.new_aggregator.service.AuthenticationService;
import com.example.new_aggregator.utils.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${spring.application.base-path}")
public class AuthenticationController
{

    @Autowired
    private AuthenticationService authenticationService;
    
    
    /**
     * Initiates a new user session.
     * @param userDto User details for session initiation.
     * @return ResponseEntity containing AuthenticationResponseDto with session status and HTTP status OK.
     */
    @PostMapping("/authenticate")
    public ResponseEntity<ResponseDto<String>> loginUser(@RequestBody UserDto userDto)
    {
        String response = authenticationService.loginUser(userDto);
        return new ResponseEntity<>(new ResponseDto<>(ResponseStatus.SUCCESS, response), HttpStatus.OK);
    }
    
    /** 
     * Verifies the email of the authenticated user.
     * @param userId ID of the user whose email needs to be verified.
     * @return ResponseEntity containing ResponseDto with success message and HTTP status OK.
     */
    @PutMapping("/verify-email")
    public ResponseEntity<ResponseDto<String>> verifyEmail(@RequestHeader("userId") Long userId)
    {
        authenticationService.verifyEmail(userId);
        return new ResponseEntity<>(new ResponseDto<>(ResponseStatus.SUCCESS, "Verified mail successfully..."), HttpStatus.OK);
    }
    

    /**
     * Logs out the authenticated user.
     * @return ResponseEntity containing ResponseDto with success message and HTTP status OK.
     */
    @PostMapping("/logout-user")
    public ResponseEntity<ResponseDto<String>> logoutUser()
    {
        authenticationService.logoutUser();
        return new ResponseEntity<>(new ResponseDto<>(ResponseStatus.SUCCESS, "User logged out successfully"), HttpStatus.OK);
    }

}
