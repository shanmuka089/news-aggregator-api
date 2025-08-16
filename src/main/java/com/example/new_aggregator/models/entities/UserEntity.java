package com.example.new_aggregator.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "users")
public class UserEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String username;
    private String email;
    private String password;
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name ="user_roles", 
            joinColumns = {
                @JoinColumn(name="user_id")
            },
            inverseJoinColumns = {
                @JoinColumn(name="role_id")
            }
    )
    private List<RoleEntity> roles;
}
