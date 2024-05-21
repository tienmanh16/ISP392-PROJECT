package com.isp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("loc")
public class list {

    @GetMapping("/room")
    public String listRoom() {
        return "room.html";
    }

    @GetMapping("/detail")
    public String detailR() {
        return "detail.html";
    }

@GetMapping("test")
    public String room(){
        return "test";
    }
}