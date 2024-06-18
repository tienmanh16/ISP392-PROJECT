package com.isp.project.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.isp.project.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Employee findByUsername(String username);

    Employee findByEmail(String email);
    
    Employee findById(int id);
    
    boolean existsByEmail(String email);

    List<Employee> findByIsActiveTrue();
    
}
