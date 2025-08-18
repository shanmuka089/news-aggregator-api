package com.example.new_aggregator.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.ArrayList;
import java.util.Date;
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
    
    @Column(nullable = false, unique = true, length = 100)
    private String email;
    
    private String password;
    private boolean disabled;
    private boolean locked;
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name ="user_roles", 
            joinColumns = {
                @JoinColumn(name="user_id")
            },
            inverseJoinColumns = {
                @JoinColumn(name="role_id")
            }
    )
    private List<RoleEntity> roles = new ArrayList<>();
    
    @CreationTimestamp
    private Date createdAt;
    
    @UpdateTimestamp
    private Date updatedAt;
}
