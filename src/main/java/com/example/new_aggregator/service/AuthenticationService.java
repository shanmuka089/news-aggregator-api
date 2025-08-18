package com.example.new_aggregator.service;

import com.example.new_aggregator.models.dto.UserDto;

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
     * Logs out the currently authenticated user.
     */
    void logoutUser();
}
