package com.isp.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.isp.project.model.GuestInformation;
import com.isp.project.service.GuestInformationService;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/guest")
public class ContactController {
    @Autowired
    private GuestInformationService guestInformationService;

    @GetMapping("/leaveInfo")
     public String leaveInfo(Model model) {
        GuestInformation guestInformation = new GuestInformation();
        model.addAttribute("guestInformation", guestInformation);

        return "contact";

    }
    @GetMapping("/listInfo")
    public String listGuestInfo(Model model ) {
        List<GuestInformation> guestInformations = this.guestInformationService.getAll();
        model.addAttribute("listInfo", guestInformations);
        return "listGuestInfo";
    }
    

    @PostMapping("/addInfo")
    public String addInfo(@ModelAttribute("guestInformation") GuestInformation guestInformation) {

        if (this.guestInformationService.create(guestInformation)) {
            return "redirect:/guest/listInfo";
        } else {
            return "redirect:/guest/leaveInfo";
        }
    }

     @PostMapping("/contact_status/{id}")
    public ResponseEntity<?> toggleStatus(@PathVariable("id") int id, @RequestParam("isContacted") boolean isContacted) {
        try {
            boolean newStatus = this.guestInformationService.contactStatus(id, isContacted);
            return ResponseEntity.ok().body(newStatus);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to toggle employee status.");
        }
    }
}
