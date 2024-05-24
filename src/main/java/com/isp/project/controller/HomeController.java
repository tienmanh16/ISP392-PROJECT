package com.isp.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
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
    public String room() {
        return "home";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }
}