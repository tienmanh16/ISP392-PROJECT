package com.isp.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.isp.project.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserName(String userName);
    User findByEmail(String email);

}
