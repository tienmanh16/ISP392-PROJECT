package com.isp.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @GetMapping("/list")
    public String listEmployee(){
        return "viewEmployee";
    }
}
