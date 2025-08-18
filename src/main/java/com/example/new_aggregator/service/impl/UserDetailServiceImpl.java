package com.example.new_aggregator.service.impl;

import com.example.new_aggregator.exception.NewsAggregatorErrorCode;
import com.example.new_aggregator.exception.NewsAggregatorException;
import com.example.new_aggregator.models.entities.UserEntity;
import com.example.new_aggregator.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserDetailServiceImpl implements UserDetailsService
{
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        Optional<UserEntity> optionalUser = userRepository.findByEmail(username);
        
        if(optionalUser.isEmpty()) {
            throw new NewsAggregatorException(NewsAggregatorErrorCode.UNAUTHORIZED);
        }
        
        UserEntity userEntity = optionalUser.get();
        
        List<SimpleGrantedAuthority> authorities = userEntity.getRoles().stream().map(roleEntity -> new SimpleGrantedAuthority(roleEntity.getRoleName())).collect(Collectors.toList());
        
        UserDetails userDetails = User
                .builder()
                .username(userEntity.getEmail())
                .password(userEntity.getPassword())
                .accountLocked(userEntity.isLocked())
                .disabled(userEntity.isDisabled())
                .authorities(authorities)
                .build();
        
        return userDetails;
    }

}
