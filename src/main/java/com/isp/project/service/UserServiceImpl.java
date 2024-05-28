package com.isp.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isp.project.model.Employee;
import com.isp.project.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService{

@Autowired
private UserRepository userRepository;

    @Override
    public Employee findByUserName(String username) {
        return userRepository.findByUserName(username);
    }

    @Override
    public Employee authenticateUser(String username, String password) {
        Employee user = userRepository.findByUserName(username);
        if (username == null || username.trim().isEmpty()) {
            return null;
        }
        if (user != null && user.getPassWord().equals(password)) {
            return user;
        } else {
            return null;
        }
    }

    @Override
    public Employee findByEmail(String email) {
        return userRepository.findByEmail(email);
    }



   
    

}
