package com.isp.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.isp.project.service.RoomService;

@Controller
@RequestMapping("")
public class HomeController {

    @Autowired
    private RoomService roomService;

    @GetMapping("/room")
    public String listRoom(Model model) {
        model.addAttribute("rooms", roomService.getAllRoomsWithDetails());
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