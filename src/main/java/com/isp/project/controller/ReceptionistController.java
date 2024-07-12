package com.isp.project.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.isp.project.model.Employee;
import com.isp.project.service.EmployeeService;


@Controller
@RequestMapping("/receptionist")
public class ReceptionistController {

	@Autowired
	private EmployeeService userRepo;

	@Autowired
	private EmployeeService employeeService;

	@ModelAttribute
	public void commonUser(Principal p, Model m) {
		if (p != null) {
			String email = p.getName();
			Employee user = userRepo.findByEmail(email);
			m.addAttribute("user", user);
		}

	}

	@GetMapping("/home")
	public String profile(Principal p,Model model) {
		if (p != null) {
            String email = p.getName();
            Employee user = employeeService.findByEmail(email);
            if (user != null) {
                model.addAttribute("user1", user);
            } else {
            }
        }
		return "home_recep";
	}

}
