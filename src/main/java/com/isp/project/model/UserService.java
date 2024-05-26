package com.isp.project.model;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;

public interface UserService {
    //controller goi den service, ko goi den repo
     @Query("SELECT email FROM User WHERE LOWER(email) = LOWER(?1)")
    String findEmailByEmailIgnoreCase(String email);

    Optional<User> findByEmailIgnoreCase(String username);
    User findByUserName(String userName);
    User authenticateUser(String username, String password);
    Boolean forgotPassword(String email);
}
