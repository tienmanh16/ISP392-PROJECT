package com.isp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("loc")
public class HomeController {

    @GetMapping("/room")
    public String listRoom() {
        return "room";
    }

    @GetMapping("/detail")
    public String detailR() {
        return "detail";
    }

@GetMapping("/home")
    public String room(){
        return "home";
    }
}