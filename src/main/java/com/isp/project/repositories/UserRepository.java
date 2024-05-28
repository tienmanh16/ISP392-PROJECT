package com.isp.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.isp.project.model.Employee;

public interface UserRepository extends JpaRepository<Employee, Long> {
    Employee findByUserName(String username);

    Employee findByEmail(String email);
}
