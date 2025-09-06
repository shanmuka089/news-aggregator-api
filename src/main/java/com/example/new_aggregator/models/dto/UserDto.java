package com.example.new_aggregator.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserDto
{
    private Long userId;
    private String username;
    private String email;
    private String password;
    private Long expirationTime;
    private List<RoleDto> roles;
    private Date createdAt;
    private Date updatedAt;
}
