package com.isp.project.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.isp.project.model.Employee;
import com.isp.project.service.EmployeeService;
import com.isp.project.service.RoomServiceImpl;
import com.isp.project.service.RoomTypeServiceImpl;

@Controller
@RequestMapping("")
public class HomeController {

    @Autowired
    private EmployeeService userService;


    @ModelAttribute
	public void commonUser(Principal p, Model m) {
		if (p != null) {
			String email = p.getName();
			Employee user = userService.findByEmail(email);
			m.addAttribute("user", user);
		}

	}

    @GetMapping("/login")
    public String login() {
        return "login";
    }


    @Autowired
    private RoomServiceImpl roomServiceImpl;

    @Autowired
    private RoomTypeServiceImpl roomTypeServiceImpl;

    // @GetMapping("/room")
    // public String listRoom(Model model) {
    // model.addAttribute("roomTypes",
    // roomTypeServiceImpl.getAllRoomTypesWithDetails());
    // model.addAttribute("rooms", roomServiceImpl.getAllRoomsWithDetails());
    // return "room";
    // }

    @PostMapping("/filterRoomType")
    public String filter(@RequestParam("selectedRoomTypeId") Integer id, Model model) {
        model.addAttribute("roomTypes", roomTypeServiceImpl.getAllRoomTypesWithDetails());
        model.addAttribute("rooms", roomServiceImpl.getAllRoomsWithDetailsByRoomTypeId(id));
        return "room";
    }

    @PostMapping("/filter-status")
    public String filterStatus(@RequestParam("statusFilter") String status, Model model) {
        model.addAttribute("roomTypes", roomTypeServiceImpl.getAllRoomTypesWithDetails());
        model.addAttribute("rooms", roomServiceImpl.getAllRoomsByStatus(status));
        return "room";
    }

    @GetMapping("/detail")
    public String detailR(@RequestParam("roomTypeId") Integer roomTypeId, Model model) {
        model.addAttribute("roomType", roomTypeServiceImpl.getRoomTypeDetailById(roomTypeId));
        return "detail";
    }

    @GetMapping("/home")
    public String room(Model model) {
        model.addAttribute("roomTypes", roomTypeServiceImpl.getAllRoomTypesWithDetails());
        return "home";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }

}
