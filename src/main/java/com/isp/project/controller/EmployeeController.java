package com.isp.project.controller;

import java.security.Principal;
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

    @ModelAttribute
    public void commonUser(Principal p, Model m) {
        if (p != null) {
            String email = p.getName();
            Employee user = employeeService.findByEmail(email);
            m.addAttribute("user", user);
        }
    }

    @GetMapping("/home")
    public String profile() {
        return "home_admin";
    }

    @GetMapping("/employee_list")
    public String listEmployee(Model model, Principal p) {
        List<Employee> emList = employeeService.findAll();
        if (p != null) {
            String email = p.getName();
            Employee user = employeeService.findByEmail(email);
            if (user != null) {
                model.addAttribute("user1", user);
            } else {
            }
        }

        model.addAttribute("emList", emList);
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
        employeeService.saveUser(employee);
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
    public String update(Model model, @Valid @ModelAttribute("employee") Employee employee, @RequestParam("password") String password, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "editEm";
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
            existingEmployee.setSalary(employee.getSalary());
            existingEmployee.setRole(employee.getRole());
            employeeService.saveUser(existingEmployee);
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

    @PostMapping("/employee_toggleLock/{employeeId}")
    public ResponseEntity<?> toggleLock(@PathVariable("employeeId") int employeeId, @RequestParam("isAccountNonLocked") boolean isAccountNonLocked) {
        try {
            boolean newLock = employeeService.toggleEmployeeLock(employeeId, isAccountNonLocked);
            return ResponseEntity.ok().body(newLock);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to toggle employee Lock.");
        }
    }

    @GetMapping("/employee_check-email")
    public ResponseEntity<Boolean> checkEmailExists(@RequestParam String email) {
        boolean exists = employeeService.existsByEmail(email);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/employee_check-username")
    public ResponseEntity<Boolean> checkNameExists(@RequestParam String username) {
        boolean exists = employeeService.existsByUsername(username);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/employee_search_active")
    public String sortEmployeesBySalary(@RequestParam("status") String status, Model model, Principal p) {
        List<Employee> emList;
        if (null == status) {
            emList = employeeService.findAll();
        } else {
            emList = switch (status) {
                case "all" ->
                    employeeService.findAll();
                case "active" ->
                    employeeService.findActiveEmployees();
                case "inactive" ->
                    employeeService.findInActiveEmployees();
                default ->
                    employeeService.findAll();
            };
        }
        if (p != null) {
            String email = p.getName();
            Employee user = employeeService.findByEmail(email);
            if (user != null) {
                model.addAttribute("user1", user);
            } else {
            }
        }
        model.addAttribute("emList", emList);
        model.addAttribute("status", status);
        return "viewEmployee";
    }

    @GetMapping("/viewProfile")
    public String getProfileEm(Model model, Principal principal) {
        String email = principal.getName();
        Employee employee = employeeService.findByEmail(email);
        model.addAttribute("employee", employee);
        return "viewProfile";
}
}