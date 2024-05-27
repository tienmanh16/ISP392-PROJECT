package com.isp.project.service;

import com.isp.project.model.User;

public interface UserService {
    //controller goi den service, ko goi den repo
    
    
    User findByUserName(String userName);
    User authenticateUser(String username, String password);
 
}
