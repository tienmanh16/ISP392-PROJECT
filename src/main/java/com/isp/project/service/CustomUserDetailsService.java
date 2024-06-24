// package com.isp.project.service;

// import java.util.ArrayList;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.userdetails.User;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.stereotype.Service;

// import com.isp.project.model.Employee;
// import com.isp.project.repositories.EmployeeRepository;

// @Service
// public class CustomUserDetailsService implements UserDetailsService{
  
//     @Autowired
//     EmployeeRepository employeeRepository;

//     @Override
//     public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//         System.out.println("check" + username);
//         Employee employee= employeeRepository.findByUsername(username);
//         if(employee==null){
//             throw new UsernameNotFoundException("User not found");
//         }
//         return new User(username, employee.getPassword(), new ArrayList<>());
//     }

// }
