package com.isp.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RoomController {

    @GetMapping("/managerbooking")
    public String ManagerBooking() {
        return "ManagerBooking";
    }

    @GetMapping("/roomcategory")
    public String RoomCategory() {
        return "RoomCategory";
    }

    
     
}
