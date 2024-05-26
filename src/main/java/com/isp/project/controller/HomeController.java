package com.isp.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.isp.project.model.User;
import com.isp.project.model.UserService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

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
        User authenticatedUser = userService.authenticateUser(username, password);
        if (authenticatedUser != null) {
            // Authentication successful, store the user in the session
            session.setAttribute("loggedInUser", authenticatedUser);
            return "home";
        } else {
            // Authentication failed, add an error message to the redirect attributes
            model.addAttribute("errorMessage", "Invalid username or password");
            return "login";
        }
    }


    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "login";
    }

    @PostMapping("/forgotpass")
    public String forgotPassword(Model model, @RequestParam(value = "email") String email){
        User u = userService.findByEmailIgnoreCase(email);
        if(userOptional.isEmpty()){
            model.addAttribute("error","Email has not used yet! Do you want to ");
            return "ForgotPassword";
        }
        User user = userOptional.get();
        if(user.getIsActive()) {
            if (!userService.forgotPassword(email)) {
                model.addAttribute("error", "This email has not used yet! Do you want to ");
                return "ForgotPassword";
            }
            model.addAttribute("ms", "Please check your email to set new password to your account");
        }else{
            model.addAttribute("ms", "Please activate your account first");

        }
        return "ForgotPassword";
    }

}
