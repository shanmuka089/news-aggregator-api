package com.example.new_aggregator.service;

import com.example.new_aggregator.models.dto.UserDto;

public interface UserService
{

    /**
     * Saves the user details to the database.
     * @param userDto UserDto containing user details to be saved.
     * @return UserDto containing the saved user details.
     */
    UserDto saveUser(UserDto userDto);
    
    
    /**
     * Fetches the user details from the database.
     * @return UserDto containing user details.
     */
    UserDto fetchUser();

}
