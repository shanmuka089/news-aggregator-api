package com.example.new_aggregator.service;

import com.example.new_aggregator.models.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthenticationService
{
    
    /**
     * Logs in a user with the provided credentials.
     *
     * @param userDto User credentials for login.
     * @return A token representing the user's session.
     */
    String loginUser(UserDto userDto);

    /**
     * Verifies the email of the currently authenticated user.
     * This method should be called after the user has logged in and needs to confirm their email address.
     */
    void verifyEmail(Long userId);
    
    
    /**
     * Logs out the currently authenticated user.
     */
    void logoutUser();
}
