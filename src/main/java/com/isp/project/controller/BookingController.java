package com.isp.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BookingController {

    @GetMapping("booking")
    public String datphong1() {
        return "datphong";
    }

    @GetMapping("bookingdetail")
    public String themPhong() {
        return "detaildatphong";
    }

    

    
}
