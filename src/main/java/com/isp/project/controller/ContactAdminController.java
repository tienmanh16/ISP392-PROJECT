package com.isp.project.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.isp.project.model.Email;
import com.isp.project.model.Employee;
import com.isp.project.model.GuestInformation;
import com.isp.project.service.EmployeeService;
import com.isp.project.service.GuestInformationService;


@Controller

public class ContactAdminController {
    @Autowired
    private GuestInformationService guestInformationService;

    @Autowired
    Email emailService;

    @Autowired
    private EmployeeService employeeService;

    // @GetMapping("/leaveInfo")
    //  public String leaveInfo(Model model) {
    //     GuestInformation guestInformation = new GuestInformation();
    //     model.addAttribute("guestInformation", guestInformation);

    //     return "contact";

    // }
    @GetMapping("/admin/listInfo")
    public String listGuestInfo(Model model, Principal p) {
        List<GuestInformation> guestInformations = this.guestInformationService.getAll();
        model.addAttribute("listInfo", guestInformations);
        
        if (p != null) {
            String email = p.getName();
            Employee user = employeeService.findByEmail(email);
            if (user != null) {
                model.addAttribute("user1", user);
            } else {
            }
        }
        return "listGuestInfoAdmin";
    }
    

    // @PostMapping("/addInfo")
    // public String addInfo(@ModelAttribute("guestInformation") GuestInformation guestInformation) throws MessagingException {

    //     if (this.guestInformationService.create(guestInformation)) {
    //         emailService.sendEmailLeaveInfo(guestInformation.getEmail(), guestInformation.getName());
    //         return "redirect:http://localhost:8080/leaveInfo";
    //     } else {
    //         return "redirect:http://localhost:8080/leaveInfo";
    //     }
    // }

     @PostMapping("/admin/contact_status/{id}")
    public ResponseEntity<?> toggleStatus(@PathVariable("id") int id, @RequestParam("isContacted") boolean isContacted) {
        try {
            boolean newStatus = this.guestInformationService.contactStatus(id, isContacted);
            return ResponseEntity.ok().body(newStatus);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to toggle employee status.");
        }
    }
}
