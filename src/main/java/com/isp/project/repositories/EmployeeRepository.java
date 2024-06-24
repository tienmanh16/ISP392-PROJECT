package com.isp.project.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isp.project.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Employee findByUsername(String username);

    Employee findByEmail(String email);
    
    Employee findById(int id);
    
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);

    List<Employee> findByIsActiveTrue();

}
