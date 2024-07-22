package com.isp.project.service;

import java.util.List;

import com.isp.project.dto.UserResetPasswordDto;
import com.isp.project.model.Employee;

public interface EmployeeService {

    //controller goi den service, ko goi den repo
    Employee findByEmail(String email);
    
    Employee findByUserName(String username);

    Object changePassword(int id, UserResetPasswordDto dto);

    Employee findById(int id);

    void deleteById(Integer id);
    List<Employee> findByRole(String role);    

    List<Employee> findAll();

    List<Employee> findInActiveReceptionists();
    List<Employee> findActiveReceptionists(); 

    Employee save(Employee entity);
    
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);

    boolean toggleEmployeeStatus(int employeeId, boolean currentStatus);
    boolean toggleEmployeeLock(int employeeId, boolean currentLock);

    public Employee saveUser(Employee employee);

	public void removeSessionMessage();

    public void increaseFailedAttempt(Employee employee);

	public void resetAttempt(String email);

	public void lock(Employee employee);

	public boolean unlockAccountTimeExpired(Employee employee);

}