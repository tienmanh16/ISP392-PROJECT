package com.isp.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.isp.project.model.Employee;
import com.isp.project.service.EmployeeService;
import com.isp.project.service.RoleService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    RoleService roleService;

    @GetMapping("/list")
    public String listEmployee(Model model){
        model.addAttribute("emList", employeeService.findAll());
        return "viewEmployee";
    }

    @GetMapping("/add")
    public String addorEdit(Model model){
        Employee employee = new Employee();
        model.addAttribute("employee", employee);
        model.addAttribute("role", roleService.findAll());
        model.addAttribute("action", "/employee/saveOrUpdate");
        return "addEm";
    }

    @PostMapping("/saveOrUpdate")
public String save(Model model, @Valid @ModelAttribute("employee") Employee employee, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
        model.addAttribute("role", roleService.findAll());
        model.addAttribute("action", "/employee/saveOrUpdate");
        return "addEmployee";
    }

    Employee existingEmployee = employeeService.findById(employee.getId());
    if (existingEmployee != null) {
        // Update the existing employee
        existingEmployee.setFullName(employee.getFullName());
        existingEmployee.setAddress(employee.getAddress());
        existingEmployee.setEmail(employee.getEmail());
        existingEmployee.setIdenId(employee.getIdenId());
        existingEmployee.setUsername(employee.getUsername());
        existingEmployee.setPassword(employee.getPassword());
        existingEmployee.setPhone(employee.getPhone());
        existingEmployee.setDob(employee.getDob());
        existingEmployee.setRole(roleService.findByName(employee.getRole().getName()));
        employeeService.save(existingEmployee);
    } else {
        // Add new employee
        employeeService.save(employee);
    }
    return "redirect:/employee/list";
}
    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") int id) {
        Employee employee = employeeService.findById(id);
        if (employee != null) {
            model.addAttribute("role", roleService.findAll());
            model.addAttribute("employee", employee);
        } else {
            model.addAttribute("role", roleService.findAll());
            model.addAttribute("employee", new Employee());
        }
        model.addAttribute("action", "/staffs/saveOrUpdate");
        return "addEmployee";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        employeeService.deleteById(id);
        return "redirect:/employee/list";
    }
    
}