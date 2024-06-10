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

    void delete(Employee entity);

    void deleteAll();

    void deleteAll(List<Employee> entities);

    void deleteAllById(Iterable<? extends Integer> ids);

    void deleteById(Integer id);

    List<Employee> findAll();

    List<Employee> findAllById(List<Integer> ids);

    Employee save(Employee entity);

    List<Employee> saveAll(List<Employee> entities);


}
