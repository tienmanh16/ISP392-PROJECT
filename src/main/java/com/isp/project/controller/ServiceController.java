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

import com.isp.project.model.ServiceType;
import com.isp.project.service.ServiceTypeServiceImpl;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin")
public class ServiceController {
    @Autowired
    private ServiceTypeServiceImpl serviceTypeServiceImpl;
    // @GetMapping("/managerbooking")
    // public String ManagerBooking() {
    // return "ManagerBooking";
    // }

    @GetMapping("/listServiceType")
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

    public ServiceController(ServiceTypeServiceImpl serviceTypeServiceImpl) {
        this.serviceTypeServiceImpl = serviceTypeServiceImpl;
    }

    @PostMapping("/addServiceType")
    public String save(@Valid @ModelAttribute("serviceType") ServiceType serviceType, BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            return "addServiceType";
        }
        if (this.serviceTypeServiceImpl.create(serviceType)) {
            return "redirect:/admin/listServiceType";
        } else {
            return "redirect:/admin/add-secate";
        }
    }

    @GetMapping("/listSe/editservice/{SeTypeID}")
    public String update(@PathVariable("SeTypeID") Integer id, Model model) {
        model.addAttribute("serviceType", serviceTypeServiceImpl.findByID(id));
        return "updateServiceType";
    }

    @PostMapping("/saveServiceType")
    public String updated(@Valid @ModelAttribute("serviceType") ServiceType serviceType, BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            return "updateServiceType";
        }
        if (this.serviceTypeServiceImpl.create(serviceType)) {
            return "redirect:/admin/listServiceType";
        } else {
            return "redirect:/admin/add-secate";
        }
    }

    @GetMapping("/listServiceTypeActive")
    public String listServiceTypeActive(Model model) {
        List<ServiceType> serviceType = serviceTypeServiceImpl.findAllActive();
        model.addAttribute("listServiceType", serviceType);
        return "ServiceCategory";
    }

    @GetMapping("/listServiceTypeInactive")
    public String listServiceTypeInactive(Model model) {
        List<ServiceType> serviceType = serviceTypeServiceImpl.findAllInactive();
        model.addAttribute("listServiceType", serviceType);
        return "ServiceCategory";
    }

    @GetMapping("/inactiveServiceType/{SeTypeID}")
    public ResponseEntity<String> inactiveServiceType(@PathVariable("SeTypeID") int id) {
        try {
            serviceTypeServiceImpl.updateServiceTypeActiveStatus(id, 0);
            return ResponseEntity.ok("Service category inactive successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to inactive service category");
        }
    }

    @GetMapping("/activeServiceType/{SeTypeID}")
    public ResponseEntity<String> activeServiceType(@PathVariable("SeTypeID") int id) {
        try {
            serviceTypeServiceImpl.updateServiceTypeActiveStatus(id, 1);
            return ResponseEntity.ok("Service category active successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to active service category");
        }
    }

    // @GetMapping("/deleteSe/{SeTypeID}")
    // public ResponseEntity<String> deleteBooking(@PathVariable("SeTypeID") Integer
    // id) {
    // try {
    // boolean deleted = serviceTypeServiceImpl.delete(id);
    // ;
    // if (deleted) {
    // return ResponseEntity.ok("Service category deleted successfully");
    // } else {
    // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    // .body("Failed to delete service category");
    // }
    // } catch (Exception e) {
    // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error
    // occurred: " + e.getMessage());
    // }
    // }

}
