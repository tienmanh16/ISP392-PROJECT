package com.isp.project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.isp.project.model.Employee;
import com.isp.project.repositories.EmployeeRepository;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private EmployeeRepository employeeRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Employee employee = employeeRepo.findByEmail(username);
        System.out.println(employee);
        if (employee == null) {
            throw new UsernameNotFoundException("User not found");
        } else {
            return new CustomUser(employee);
        }
    }
}