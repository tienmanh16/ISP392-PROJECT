package com.isp.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.isp.project.model.Employee;
import com.isp.project.service.UserService;

import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("")
public class HomeController {

    @Autowired
    private UserService userService;


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


    
}
