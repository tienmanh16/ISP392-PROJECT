package com.isp.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isp.project.model.Employee;
import com.isp.project.repositories.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee findByUserName(String username) {
        return employeeRepository.findByUsername(username);
    }

    @Override
    public Employee authenticateUser(String username, String password) {
        Employee user = employeeRepository.findByUsername(username);
        if (username == null || username.trim().isEmpty()) {
            return null;
        }
        if (user != null && user.getPassword().equals(password)) {
            return user;
        } else {
            return null;
        }
    }

    @Override
    public Employee findByEmail(String email) {
        return employeeRepository.findByEmail(email);
    }

    @Override
    public Object changePassword(int id, String newPassword) {
            // Kiểm tra xem người dùng có tồn tại không
            Employee user = findById(id);
            if (user == null) {
                return null; // Người dùng không tồn tại
            } else {
                // Cập nhật mật khẩu mới cho người dùng, có thể mã hóa security.encode(newPassword)
                user.setPassword(newPassword);
                employeeRepository.save(user);
            }
    
            return user;
    }

    @Override
    public Employee findById(int id) {
        return employeeRepository.findById(id);
    }


    @Override
    public void deleteById(Integer id) {
        Employee employee = findById(id);
        if (employee != null) {
            employee.setIsActive(false);
            save(employee);
        }
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    // @Override
    // public List<Employee> findActiveEmployees() {
    //     return employeeRepository.findByIsActiveTrue();
    // }

    @Override
    public Employee save(Employee entity) {
        return employeeRepository.save(entity);
    }

    @Override
    public boolean existsByEmail(String email) {
        return employeeRepository.existsByEmail(email);
    }
    
    @Override
    public boolean existsByUsername(String email) {
        return employeeRepository.existsByUsername(email);
    }

    @Override
    public boolean toggleEmployeeStatus(int employeeId, boolean currentStatus) {
        Employee employee = employeeRepository.findById(employeeId);
        if (employee == null) {
            throw new IllegalArgumentException("Employee not found with ID: " + employeeId);
        }
        
        // Update the status
        employee.setIsActive(!currentStatus);
        employeeRepository.save(employee);

        // Return the new status
        return !currentStatus;
    }


}
