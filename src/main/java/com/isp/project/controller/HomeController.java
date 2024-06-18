package com.isp.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.isp.project.model.Employee;
import com.isp.project.service.EmployeeService;
import com.isp.project.service.RoomServiceImpl;
import com.isp.project.service.RoomTypeServiceImpl;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("")
public class HomeController {

    @Autowired
    private EmployeeService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
            @RequestParam("password") String password,
            Model model,
            HttpSession session) {
        Employee authenticatedUser = userService.authenticateUser(username, password);
        if (authenticatedUser != null) {
            // Authentication successful, store the user in the session
            session.setAttribute("loggedInUser", authenticatedUser);
            return "home";
        } else {
            // Authentication failed, add an error message to the redirect attributes
            model.addAttribute("loginError", "Invalid username or password");
            model.addAttribute("activeTab", "login");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "login";
    }

    @Autowired
    private RoomServiceImpl roomServiceImpl;

    @Autowired
    private RoomTypeServiceImpl roomTypeServiceImpl;

    // @GetMapping("/room")
    // public String listRoom(Model model) {
    //     model.addAttribute("roomTypes", roomTypeServiceImpl.getAllRoomTypesWithDetails());
    //     model.addAttribute("rooms", roomServiceImpl.getAllRoomsWithDetails());
    //     return "room";
    // }

    @PostMapping("/filterRoomType")
    public String filter(@RequestParam("selectedRoomTypeId") Integer id, Model model){
        model.addAttribute("roomTypes", roomTypeServiceImpl.getAllRoomTypesWithDetails());
        model.addAttribute("rooms", roomServiceImpl.getAllRoomsWithDetailsByRoomTypeId(id));
        return "room";
    }

    @PostMapping("/filter-status")
    public String filterStatus(@RequestParam("statusFilter") String status, Model model){
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
