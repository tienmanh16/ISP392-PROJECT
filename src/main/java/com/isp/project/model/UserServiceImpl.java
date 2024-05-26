package com.isp.project.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public Boolean forgotPassword(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        try {
            emailUtil.sendSetPasswordEmail(email);
        } catch (Exception e) {
            System.out.println("Unable to send otp please try again");
            return false;
        }
        return true;
    }

   
    

}
