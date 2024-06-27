package com.isp.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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

import com.isp.project.model.Room;
import com.isp.project.model.RoomType;
import com.isp.project.model.Service;
import com.isp.project.model.ServiceType;
import com.isp.project.repositories.ServiceRepository;
import com.isp.project.service.SeService;
import com.isp.project.service.ServiceTypeService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin")
public class ServiceController {

    @Autowired
    private ServiceTypeService serviceTypeService;

    @Autowired
    private SeService seService;

    @Autowired
    private ServiceRepository serviceRepository;

    @GetMapping("/listServiceType")
    public String ServiceType(Model model, @Param("name") String name) {
        List<ServiceType> listServiceType;
        if (name != null) {
            listServiceType = this.serviceTypeService.searchServiceType(name);
        } else {
            listServiceType = this.serviceTypeService.getAll();

        }
        model.addAttribute("listServiceType", listServiceType);
        return "ServiceCategory";
    }

    @GetMapping("/add-secate")
    public String add1(Model model) {
        ServiceType serviceType = new ServiceType();
        model.addAttribute("serviceType", serviceType);
        return "addServiceType";
    }

    @PostMapping("/addServiceType")
    public String save(@Valid @ModelAttribute("serviceType") ServiceType serviceType, BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            return "addServiceType";
        }
        if (this.serviceTypeService.create(serviceType)) {
            return "redirect:/admin/listServiceType";
        } else {
            return "redirect:/admin/add-secate";
        }
    }

    @GetMapping("/listSe/editservice/{SeTypeID}")
    public String update(@PathVariable("SeTypeID") Integer id, Model model) {
        model.addAttribute("serviceType", serviceTypeService.findByID(id));
        return "updateServiceType";
    }

    @PostMapping("/saveServiceType")
    public String updated(@Valid @ModelAttribute("serviceType") ServiceType serviceType, BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            return "updateServiceType";
        }
        if (this.serviceTypeService.create(serviceType)) {
            return "redirect:/admin/listServiceType";
        } else {
            return "redirect:/admin/add-secate";
        }
    }

    @GetMapping("/listServiceTypeActive")
    public String listServiceTypeActive(Model model) {
        List<ServiceType> serviceType = serviceTypeService.findAllActive();
        model.addAttribute("listServiceType", serviceType);
        return "ServiceCategory";
    }

    @GetMapping("/listServiceTypeInactive")
    public String listServiceTypeInactive(Model model) {
        List<ServiceType> serviceType = serviceTypeService.findAllInactive();
        model.addAttribute("listServiceType", serviceType);
        return "ServiceCategory";
    }

    @GetMapping("/inactiveServiceType/{SeTypeID}")
    public ResponseEntity<String> inactiveServiceType(@PathVariable("SeTypeID") int id) {
        try {
            serviceTypeService.updateServiceTypeActiveStatus(id, 0);
            return ResponseEntity.ok("Service category inactive successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to inactive service category");
        }
    }

    @GetMapping("/activeServiceType/{SeTypeID}")
    public ResponseEntity<String> activeServiceType(@PathVariable("SeTypeID") int id) {
        try {
            serviceTypeService.updateServiceTypeActiveStatus(id, 1);
            return ResponseEntity.ok("Service category active successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to active service category");
        }
    }

    // @GetMapping("/deleteSe/{SeTypeID}")
    // public ResponseEntity<String> deleteBooking(@PathVariable("SeTypeID") Integer
    // id) {
    // try {
    // boolean deleted = serviceTypeService.delete(id);
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



    //Service
    @GetMapping("/listServices")
    public String ServiceList(Model model, @Param("name") String name) {
        List<Service> services;
        if (name != null ) {
            services = this.seService.searchService(name);
        } else {
            services = this.seService.findAll();
            //services = serviceRepository.findAll();
        }
        //services = serviceRepository.findAll();
        model.addAttribute("services", services);
        return "serviceList";
    }



    @GetMapping("/add-service")
    public String addService(Model model) {
        Service service = new Service();
        List<ServiceType> serviceTypes = serviceTypeService.getAll();
        model.addAttribute("service", service);
        model.addAttribute("serviceTypes", serviceTypes);
        return "addService";
    }

    @PostMapping("/addService")
    public String saveRoom(@Valid @ModelAttribute("service") Service service, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "addService";
        }
        if (this.seService.create(service)) {
            return "redirect:/admin/listServices";
        } else {
            return "redirect:/admin/add-service";
        }
    }

    // @GetMapping("/service_check-serviceName")
    // public ResponseEntity<Boolean> checkRoomNumExists(@RequestParam String seName) {
    //     boolean exists = seService.existsByServiceName(seName);
    //     return ResponseEntity.ok(exists);
    // }

    @GetMapping("/listServices/{id}/update")
    public String editService(@PathVariable("id") int id, Model model) {
        Service service = seService.findBySeId(id);
        if (service == null) {
            return "redirect:/admin/listServices";
        }
        model.addAttribute("service", service);
        List<ServiceType> serviceTypes = serviceTypeService.getAll();
        model.addAttribute("serviceTypes", serviceTypes);
        return "updateService";
    }

    @PostMapping("/saveService")
    public String updateService(@Valid @ModelAttribute("service") Service service, BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            return "updateService"; // Trả về lại trang hiện tại nếu có lỗi
        }
        if (this.seService.create(service)) {
            return "redirect:/admin/listServices";
        } else {
            return "redirect:/admin/add-service";
        }
    }


    @GetMapping("/inactiveService/{SeID}")
    public ResponseEntity<String> inactiveService(@PathVariable("SeID") int id) {
        try {
            seService.updateServiceActiveStatus(id, 0);
            return ResponseEntity.ok("Service inactive successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to inactive service");
        }
    }

    @GetMapping("/activeService/{SeID}")
    public ResponseEntity<String> activeService(@PathVariable("SeID") int id) {
        try {
            seService.updateServiceActiveStatus(id, 1);
            return ResponseEntity.ok("Service active successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to active service");
        }
    }

}
