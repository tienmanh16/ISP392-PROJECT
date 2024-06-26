package com.isp.project.service;

import java.util.List;

import com.isp.project.model.Booking;
import com.isp.project.model.Employee;

public interface EmployeeService {

    //controller goi den service, ko goi den repo
    Employee findByEmail(String email);
    
    Employee findByUserName(String username);

    Employee authenticateUser(String username, String password);
 
    Object changePassword(int id, String newPassword);

    Employee findById(int id);

    void deleteById(Integer id);

    List<Employee> findAll();

    // List<Employee> findActiveEmployees();
    Employee save(Employee entity);
    
    boolean existsByEmail(String email);
    boolean toggleEmployeeStatus(int employeeId, boolean currentStatus);


}
