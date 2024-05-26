package com.isp.project.model;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserName(String userName);

    @Query("SELECT email FROM User WHERE LOWER(email) = LOWER(?1)")
    String findEmailByEmailIgnoreCase(String email);

    Optional<User> findByEmailIgnoreCase(String username);
}
