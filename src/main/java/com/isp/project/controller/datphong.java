package com.isp.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class datphong {

    @GetMapping("datphong")
    public String datphong1() {
        return "datphong";
    }

    @GetMapping("detaildatphong")
    public String themPhong() {
        return "detaildatphong";
    }

}
