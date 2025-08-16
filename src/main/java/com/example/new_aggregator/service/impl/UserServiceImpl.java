package com.example.new_aggregator.service.impl;

import com.example.new_aggregator.client.GNewsClient;
import com.example.new_aggregator.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService
{
    @Autowired
    private GNewsClient gNewsClient;

    public void test() {
        System.out.println("User Service invoked");
        gNewsClient.fetchNewsByQuery(null);
    }
}
