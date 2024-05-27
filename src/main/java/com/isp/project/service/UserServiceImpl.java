package com.isp.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isp.project.model.User;
import com.isp.project.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService{

@Autowired
private UserRepository userRepository;

    @Override
    public User findByUserName(String userName) {
        // TODO Auto-generated method stub
        return userRepository.findByUserName(userName);
    }

    @Override
    public User authenticateUser(String username, String password) {
        User user = userRepository.findByUserName(username);
        if (username == null || username.trim().isEmpty()) {
            return null;
        }
        if (user != null && user.getPassWord().equals(password)) {
            return user;
        } else {
            return null;
        }
    }



   
    

}
