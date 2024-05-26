package com.isp.project.service;

import org.springframework.data.jpa.repository.Query;

import com.isp.project.model.User;

public interface UserService {
    //controller goi den service, ko goi den repo
     @Query("SELECT email FROM User WHERE LOWER(email) = LOWER(?1)")
    String findEmailByEmailIgnoreCase(String email);
    
    User findByUserName(String userName);
    User authenticateUser(String username, String password);
    Boolean forgotPassword(String email);
}
