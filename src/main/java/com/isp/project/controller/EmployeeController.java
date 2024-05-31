package com.isp.project.controller;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.isp.project.model.Employee;
import com.isp.project.service.EmployeeService;
import com.isp.project.service.RoleService;


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
        return "addEmployee";
    }

    @PostMapping("/saveOrUpdate")
    public String save(Model model, @ModelAttribute("employee") Employee employee) {
        Employee existingEmployee = employeeService.findById(employee.getId());
    if (existingEmployee != null) {
        //save
    } else {
        // Add new staff
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
        model.addAttribute("action", "/staffs/saveOrUpdate");
        return "viewEmployee";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        employeeService.deleteById(id);
        List<Employee> employees = employeeService.findAll();
        for (int i = 0; i < employees.size(); i++) {
            employees.get(i).setId(i + 1);
            employeeService.save(employees.get(i));
        }
        return "redirect:/employee/list";
    }
    
}