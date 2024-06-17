package com.isp.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/admin")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping("/employee_list")
    public String listEmployee(Model model, @RequestParam(name = "name", required = false) String name) {
        List<Employee> listEmployee;
        if (name != null && !name.isEmpty()) {
            listEmployee = employeeService.searchName(name);
        } else {
            listEmployee = employeeService.findAll();
        }
        model.addAttribute("emList", listEmployee);
        return "viewEmployee";
    }


    @GetMapping("/employee_add")
    public String add(Model model) {
        Employee employee = new Employee();
        model.addAttribute("employee", employee);
        return "addEm";
    }

    @PostMapping("/employee_add")
    public String save(Model model, @Valid @ModelAttribute("employee") Employee employee, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "addEm";
        }
        employee.setIsActive(true);
        employeeService.save(employee);
        return "redirect:/admin/employee_list";
    }
    
    @GetMapping("/employee_edit/{id}")
    public String edit(Model model, @PathVariable("id") int id) {
        Employee employee = employeeService.findById(id);
        if (employee != null) {
            model.addAttribute("employee", employee);
            return "editEm"; 
        } else {
            return "redirect:/admin/employee_list"; 
        }
    }

    @PostMapping("/employee_edit")
    public String update(Model model, @Valid @ModelAttribute("employee") Employee employee, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "editEm";
        }

        Employee existingEmployee = employeeService.findById(employee.getId());
        if (existingEmployee != null) {
            existingEmployee.setFullName(employee.getFullName());
            existingEmployee.setGender(employee.getGender());
            existingEmployee.setAddress(employee.getAddress());
            existingEmployee.setEmail(employee.getEmail());
            existingEmployee.setIdenId(employee.getIdenId());
            existingEmployee.setUsername(employee.getUsername());
            // existingEmployee.setPassword(employee.getPassword());
            existingEmployee.setPhone(employee.getPhone());
            existingEmployee.setDob(employee.getDob());
            existingEmployee.setSalary(employee.getSalary());
            existingEmployee.setRole(employee.getRole());
            existingEmployee.setIsActive(true); 
            employeeService.save(existingEmployee);
        }
        return "redirect:/admin/employee_list";
        
    }

    @GetMapping("/employee_delete/{id}")
    public String delete(@PathVariable("id") int id) {
        Employee employee = employeeService.findById(id);
        if (employee != null) {
            employee.setIsActive(false);
            employeeService.save(employee);
        }
        return "redirect:/admin/employee_list";
    }

    @PostMapping("/employee_toggleStatus/{employeeId}")
    public ResponseEntity<?> toggleStatus(@PathVariable("employeeId") int employeeId, @RequestParam("isActive") boolean isActive) {
        try {
            boolean newStatus = employeeService.toggleEmployeeStatus(employeeId, isActive);
            return ResponseEntity.ok().body(newStatus);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to toggle employee status.");
        }
    }

    @GetMapping("/employee_check-email")
    public ResponseEntity<Boolean> checkEmailExists(@RequestParam String email) {
        boolean exists = employeeService.existsByEmail(email);
        return ResponseEntity.ok(exists);
    }


}
