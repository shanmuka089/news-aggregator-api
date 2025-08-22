package com.example.new_aggregator.controller;

import com.example.new_aggregator.config.errorHandling.ErrorResolver;
import com.example.new_aggregator.models.dto.UserDto;
import com.example.new_aggregator.service.AuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthenticationController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthenticationControllerTest
{

    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthenticationService authenticationService;

    @MockitoBean
    private ErrorResolver errorResolver;
    
    @Test
    void testLoginUser() throws Exception
    {
        
        when(authenticationService.loginUser(any(UserDto.class))).thenReturn("Login successful");
        UserDto userDto = new UserDto();
        userDto.setEmail("test@gmail.com");
        userDto.setPassword("testPassword");
        
        mockMvc.perform(post("/news-aggregator/api/v1/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"))
                .andExpect(jsonPath("$.data").value("Login successful"));
    }
    
    @Test
    void verifyEmail() {
        
        doNothing().when(authenticationService).verifyEmail(any(Long.class));
        Long userId = 1L;
        try {
            mockMvc.perform(put("/news-aggregator/api/v1/verify-email")
                            .header("userId", userId))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value("SUCCESS"))
                    .andExpect(jsonPath("$.data").value("Verified mail successfully..."));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    void logoutUser() {
        doNothing().when(authenticationService).logoutUser();
        try {
            mockMvc.perform(post("/news-aggregator/api/v1/logout-user"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value("SUCCESS"))
                    .andExpect(jsonPath("$.data").value("User logged out successfully"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
}
