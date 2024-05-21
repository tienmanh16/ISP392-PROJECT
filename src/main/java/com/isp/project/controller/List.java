package com.isp.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class List {
    @GetMapping("/index")
    public String listRoom() {
        return "index";
    }

    @GetMapping("/detail")
    public String detailR() {
        return "detail";
    }

}
