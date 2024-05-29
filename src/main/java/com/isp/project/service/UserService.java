package com.isp.project.service;

import com.isp.project.model.Employee;

public interface UserService {
    //controller goi den service, ko goi den repo
    Employee findByEmail(String email);
    
    Employee findByUserName(String username);

    Employee authenticateUser(String username, String password);
 
    Object changePassword(int id, String newPassword);

    Employee findById(int id);

}
