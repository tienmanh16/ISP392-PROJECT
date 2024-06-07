package com.isp.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.isp.project.model.ServiceType;
import com.isp.project.service.ServiceTypeServiceImpl;

@Controller
public class ServiceController {
    @Autowired
    private ServiceTypeServiceImpl serviceTypeServiceImpl;
    // @GetMapping("/managerbooking")
    // public String ManagerBooking() {
    //     return "ManagerBooking";
    // }

    @GetMapping("/servicecategory")
    public String ServiceType(Model model) {
        model.addAttribute("listServiceType", serviceTypeServiceImpl.getAll());
        return "ServiceCategory";
    }

    @GetMapping("/add-secate")
    public String add1(Model model) {
        ServiceType serviceType = new ServiceType();
        model.addAttribute("serviceType", serviceType);
        return "addServiceType";
    }

    @PostMapping("/addServiceType")
    public String save(@ModelAttribute("serviceType") ServiceType serviceType) {
       
        if (this.serviceTypeServiceImpl.create(serviceType)) {
            return "redirect:/servicecategory";
        } else {
            return "redirect:/add-secate";
        }
    }

    @GetMapping("/listSe/editservice/{SeTypeID}")
    public String update(@PathVariable("SeTypeID") Integer id, Model model) {
        model.addAttribute("serviceType", serviceTypeServiceImpl.findByID(id));
        return "updateServiceType";
    }

    @PostMapping("/saveServiceType")
    public String updated(@ModelAttribute("serviceType") ServiceType serviceType) {
        if (this.serviceTypeServiceImpl.create(serviceType)) {
            return "redirect:/servicecategory";
        } else {
            return "redirect:/add-secate";
        }
    }

    @GetMapping("/deleteSe/{SeTypeID}")
    public ResponseEntity<String> deleteBooking(@PathVariable("SeTypeID") Integer id) {
        try {
            boolean deleted = serviceTypeServiceImpl.delete(id);;
            if (deleted) {
                return ResponseEntity.ok("Service category deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete service category");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

}
