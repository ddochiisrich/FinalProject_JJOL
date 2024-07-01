package com.example.project_jjol.service;

import com.example.project_jjol.model.User;
import com.example.project_jjol.repository.UserMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public void saveUser(User user) {
        userMapper.saveUser(user);
    }

    public User findById(String userId) {
        return userMapper.findById(userId);
    }
}
