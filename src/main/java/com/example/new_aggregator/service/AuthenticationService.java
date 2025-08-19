package com.example.new_aggregator.service;

import com.example.new_aggregator.models.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthenticationService
{

    /**
     * Initiating Session
     * @param request
     * @return
     */
    String initAuth(HttpServletRequest request);
    
    /**
     * Logs in a user with the provided credentials.
     *
     * @param userDto User credentials for login.
     * @return A token representing the user's session.
     */
    String loginUser(UserDto userDto);

    /**
     * Sending OTp
     * @param otpType
     * @return
     */
    String sendOtp(String otpType);

    /**
     * Validating Otp
     * @param otp
     * @return
     */
    String validateOtp(String otp);
    
    /**
     * Logs out the currently authenticated user.
     */
    void logoutUser();
}
