package com.isp.project.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.isp.project.dto.UserResetPasswordDto;
import com.isp.project.model.Employee;
import com.isp.project.repositories.EmployeeRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

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
    public Object changePassword(int id, UserResetPasswordDto dto) {
        // Kiểm tra xem người dùng có tồn tại không
        Employee user = findById(id);
        if (user == null) {
            return null; // Người dùng không tồn tại
        } else {
            String password = passwordEncoder.encode(dto.getNewPassword());
            user.setPassword(password);
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
        return employeeRepository.findByRole("ROLE_RECEPTIONIST");
    }

    // @Override
    // public List<Employee> findActiveEmployees() {
    // return employeeRepository.findByIsActiveTrue();
    // }

    @Override
   public List<Employee> findActiveEmployees() {
    // Fetch all inactive employees from the repository
    List<Employee> raw_list = employeeRepository.findByIsActiveTrue();
    
    // Create a new list to hold employees with the role 'Receptionist'
    List<Employee> receptionistList = new ArrayList<>();

    // Iterate through the raw list and add employees with the role 'Receptionist' to the receptionistList
    for (Employee emp : raw_list) {
        if (emp.getRole().equalsIgnoreCase("role_Receptionist")) {
            receptionistList.add(emp);
        }
    }
    
    // Return the list of receptionists
    return receptionistList;
}

    // @Override
    // public List<Employee> findInActiveEmployees() {
    // return employeeRepository.findByIsActiveFalse();
    // }
    @Override
    public List<Employee> findInActiveEmployees() {
            List<Employee> raw_list = employeeRepository.findByIsActiveFalse();
    
            // Create a new list to hold employees with the role 'Receptionist'
            List<Employee> receptionistList = new ArrayList<>();
        
            // Iterate through the raw list and add employees with the role 'Receptionist' to the receptionistList
            for (Employee emp : raw_list) {
                if (emp.getRole().equalsIgnoreCase("role_Receptionist")) {
                    receptionistList.add(emp);
                }
            }
            
            // Return the list of receptionists
            return receptionistList;
    }

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

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public Employee saveUser(Employee user) {

        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        // user.setRole("ROLE_RECEPTIONIST");
        user.setAccountNonLocked(true);
        user.setFailedAttempt(0);
        user.setLockTime(null);
        user.setVerifyCode(UUID.randomUUID().toString());
        Employee newuser = employeeRepository.save(user);
        return newuser;
    }

    @Override
    public void removeSessionMessage() {

        HttpSession session = ((ServletRequestAttributes) (RequestContextHolder.getRequestAttributes())).getRequest()
                .getSession();
        session.removeAttribute("msg");
    }

    @Override
    public void increaseFailedAttempt(Employee user) {

        int attempt = user.getFailedAttempt() + 1;

        employeeRepository.updateFailedAttempt(attempt, user.getEmail());

    }

    private static final long lock_time = 24 * 60 * 60 * 1000;
    // private static final long lock_duration_time = 30000;

    public static final long ATTEMPT_TIME = 3;

    @Override
    public void resetAttempt(String email) {
        employeeRepository.updateFailedAttempt(0, email);
    }

    @Override
    public void lock(Employee user) {
        user.setAccountNonLocked(false);
        user.setLockTime(new Date());
        employeeRepository.save(user);
    }

    @Override
    public boolean unlockAccountTimeExpired(Employee user) {
        if (user.getLockTime() == null) {
            return false;
        }
        long lockTimeInMills = user.getLockTime().getTime();
        long currentTimeMillis = System.currentTimeMillis();

        if (lockTimeInMills + lock_time < currentTimeMillis) {
            user.setAccountNonLocked(true);
            user.setLockTime(null);
            user.setFailedAttempt(0);
            employeeRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean toggleEmployeeLock(int employeeId, boolean currentLock) {
         Employee employee = employeeRepository.findById(employeeId);
    if (employee == null) {
        throw new IllegalArgumentException("Employee not found with ID: " + employeeId);
    }

    // Update the lock status
    boolean newLockStatus = !currentLock;
    employee.setAccountNonLocked(newLockStatus);

    // Set lockTime to null if the account is unlocked
    if (newLockStatus) {
        employee.setLockTime(null);
        employee.setFailedAttempt(0);
    } else {
        employee.setLockTime(new Date());
    }

    employeeRepository.save(employee);

    // Return the new lock status
    return newLockStatus;
    }

    @Override
    public List<Employee> findByRole(String role) {
        return employeeRepository.findByRole(role);
    }
}