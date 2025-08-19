package com.example.new_aggregator.controller;

import com.example.new_aggregator.models.dto.AuthenticationResponseDto;
import com.example.new_aggregator.models.dto.ResponseDto;
import com.example.new_aggregator.models.dto.UserDto;
import com.example.new_aggregator.service.AuthenticationService;
import com.example.new_aggregator.utils.AuthenticationStatus;
import com.example.new_aggregator.utils.ResponseStatus;
import jakarta.servlet.http.HttpServletRequest;
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
     * Initiates the session for authentication flow.
     * @param request
     * @return
     */
    @GetMapping("/init")
    public ResponseEntity<AuthenticationResponseDto<String>> initiateAuthSession(HttpServletRequest request) {
        String response = authenticationService.initAuth(request);
        return new ResponseEntity<>(new AuthenticationResponseDto<>(ResponseStatus.SUCCESS, AuthenticationStatus.INITIATE_SESSION, AuthenticationStatus.AUTHENTICATE, response), HttpStatus.OK);
    }
    
    /**
     * Updates the authenticated user's details.
     * @param userDto UserDto containing updated user details.
     * @return ResponseEntity containing ResponseDto with UserDto and HTTP status OK.
     */
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponseDto<String>> loginUser(@RequestBody UserDto userDto)
    {
        String response = authenticationService.loginUser(userDto);
        return new ResponseEntity<>(new AuthenticationResponseDto<>(ResponseStatus.SUCCESS, AuthenticationStatus.AUTHENTICATE, AuthenticationStatus.SEND_OTP, response), HttpStatus.OK);
    }

    /**
     * User opting the method of OTP to Send
     * @param otpType
     * @return
     */
    @PostMapping("/sendOtp")
    public ResponseEntity<AuthenticationResponseDto<String>> sendOtp(@RequestHeader(value = "otpType", required = true) String otpType) {
        
        String otp = authenticationService.sendOtp(otpType);
        return ResponseEntity.ok(new AuthenticationResponseDto<>(ResponseStatus.SUCCESS, AuthenticationStatus.SEND_OTP, AuthenticationStatus.VALIDATE_OTP, otp));
    }

    /**
     * Taking otp from user & Validating to complete the authentication
     * @param otpValue
     * @return
     */
    @PostMapping("/validateOtp")
    public ResponseEntity<AuthenticationResponseDto<String>> validateOtp(@RequestHeader(value = "otpValue", required = true) String otpValue) {

        String token = authenticationService.validateOtp(otpValue);
        return ResponseEntity.ok(new AuthenticationResponseDto<>(ResponseStatus.SUCCESS, AuthenticationStatus.VALIDATE_OTP, AuthenticationStatus.COMPLETED, token));
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
