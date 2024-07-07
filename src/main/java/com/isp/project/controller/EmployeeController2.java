package com.isp.project.controller;

import com.isp.project.model.Employee;
import com.isp.project.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/receptionist")
public class EmployeeController2 {

    @Autowired
    EmployeeService employeeService;

    @GetMapping("/profileEm")
    public String getProfileEm(Model model, Principal principal) {
        String email = principal.getName();
        Employee employee = employeeService.findByEmail(email);
        model.addAttribute("employee", employee);
        return "profileEm";
    }

    @GetMapping("/employee_edit/{id}")
    public String edit(Model model, @PathVariable("id") int id) {
        Employee employee = employeeService.findById(id);
        if (employee != null) {
            model.addAttribute("employee", employee);
            return "editEmbyRecep";
        } else {
            return "redirect:/receptionist/profileEm";
        }
    }

    @PostMapping("/employee_edit")
    public String update(Model model, @Valid @ModelAttribute("employee") Employee employee, @RequestParam("password") String password, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "editEmbyRecep";
        }

        Employee existingEmployee = employeeService.findById(employee.getId());
        if (existingEmployee != null) {
            existingEmployee.setPassword(employee.getPassword());
            existingEmployee.setFullName(employee.getFullName());
            existingEmployee.setGender(employee.getGender());
            existingEmployee.setAddress(employee.getAddress());
            existingEmployee.setEmail(employee.getEmail());
            existingEmployee.setIdenId(employee.getIdenId());
            existingEmployee.setUsername(employee.getUsername());
            existingEmployee.setPassword(employee.getPassword());
            existingEmployee.setPhone(employee.getPhone());
            existingEmployee.setDob(employee.getDob());
            // Không cập nhật lại salary và role
            // existingEmployee.setSalary(employee.getSalary());
            // existingEmployee.setRole(employee.getRole());
            employeeService.saveUser(existingEmployee);
        }
        return "redirect:/receptionist/profileEm";
    }



}