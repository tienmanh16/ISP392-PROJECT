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
        if (user != null && user.getPassWord().equals(password)) {
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

    public void delete(Employee entity) {
        employeeRepository.delete(entity);
    }

    public void deleteAll() {
        employeeRepository.deleteAll();
    }

    public void deleteAll(List<Employee> entities) {
        employeeRepository.deleteAll(entities);
    }

    public void deleteAllById(Iterable<? extends Integer> ids) {
        employeeRepository.deleteAllById(ids);
    }

    public void deleteById(Integer id) {
        employeeRepository.deleteById(id);
    }

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public List<Employee> findAllById(List<Integer> ids) {
        return (List<Employee>) employeeRepository.findAllById(ids);
    }

    public Employee save(Employee entity) {
        return employeeRepository.save(entity);
    }

    public List<Employee> saveAll(List<Employee> entities) {
        return (List<Employee>) employeeRepository.saveAll(entities);
    }
   


}
