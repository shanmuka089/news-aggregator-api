package com.example.new_aggregator.repository;

import com.example.new_aggregator.models.entities.RoleEntity;
import com.example.new_aggregator.models.entities.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class UserRepositoryTest
{

    @Autowired
    private UserRepository userRepository;
    
    @BeforeEach
    void setup() {
        System.setProperty("spring.profiles.active", "test");
    }
    
    @Test
    void testFindByEmail() {

        UserEntity user = new UserEntity();
        user.setUsername("testuser");
        user.setEmail("test@gmail.com");
        user.setDisabled(false);
        user.setPassword("testpassword");
        user.setLocked(false);
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setRoleName("ROLE_USER");
        roleEntity.getUsers().add(user);
        user.getRoles().add(roleEntity);
        userRepository.save(user);
        
        Optional<UserEntity> optionalUser = userRepository.findByEmail("test@gmail.com");
        
        assertTrue(optionalUser.isPresent(), "User should be found by email");
        assertEquals("testuser", optionalUser.get().getUsername(), "Username should match");
        
        userRepository.deleteAll();
        
        Optional<UserEntity> optionalUser1 = userRepository.findByEmail("test@gmail.com");
        assertTrue(optionalUser1.isEmpty(), "User should be deleted and not found by email");
    }
}
