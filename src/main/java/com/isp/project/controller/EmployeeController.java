package com.isp.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.isp.project.model.Employee;
import com.isp.project.service.EmployeeService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping("/list")
    public String listEmployee(Model model){
        model.addAttribute("emList", employeeService.findActiveEmployees());
        return "viewEmployee";
    }

    @GetMapping("/add")
    public String addorEdit(Model model){
        Employee employee = new Employee();
        model.addAttribute("employee", employee);
        model.addAttribute("action", "/employee/saveOrUpdate");
        return "addEm";
    }

    @PostMapping("/saveOrUpdate")
    public String save(Model model, @Valid @ModelAttribute("employee") Employee employee, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("action", "/employee/saveOrUpdate");
            return "editEm";
        }
        boolean exists = employeeService.existsByEmail(employee.getEmail());

        if(exists){
            model.addAttribute("emailError", "Email already exists.");
            return "editEm";
        }
        Employee existingEmployee = employeeService.findById(employee.getId());
        if (existingEmployee != null) {
            // Update the existing employee
            existingEmployee.setFullName(employee.getFullName());
            existingEmployee.setGender(employee.getGender());
            existingEmployee.setAddress(employee.getAddress());
            existingEmployee.setEmail(employee.getEmail());
            existingEmployee.setIdenId(employee.getIdenId());
            existingEmployee.setUsername(employee.getUsername());
            existingEmployee.setPassword(employee.getPassword());
            existingEmployee.setPhone(employee.getPhone());
            existingEmployee.setDob(employee.getDob());
            existingEmployee.setSalary(employee.getSalary());
            existingEmployee.setRole(employee.getRole());
            existingEmployee.setIsActive(true);  // Ensure the employee is active
            employeeService.save(existingEmployee);
        } else {
            // Add new employee
            employee.setIsActive(true);  // Ensure the employee is active
            employeeService.save(employee);
        }
        return "redirect:/employee/list";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") int id) {
        Employee employee = employeeService.findById(id);
        if (employee != null) {
            model.addAttribute("employee", employee);
        } else {
            model.addAttribute("employee", new Employee());
        }
        model.addAttribute("action", "/employee/saveOrUpdate");
        return "editEm";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        Employee employee = employeeService.findById(id);
        if (employee != null) {
            employee.setIsActive(false);  
            employeeService.save(employee);
        }
        return "redirect:/employee/list";
    }

    @GetMapping("/check-email")
    public ResponseEntity<Boolean> checkEmailExists(@RequestParam String email) {
        boolean exists = employeeService.existsByEmail(email);
        return ResponseEntity.ok(exists);
    }
}
