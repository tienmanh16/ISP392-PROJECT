package com.isp.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.isp.project.dto.UserDTO;
import com.isp.project.model.Email;
import com.isp.project.model.Employee;
import com.isp.project.service.UserService;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;

@Controller
public class ForgotPasswordController {

    @Autowired
    HttpSession session;

    @Autowired
    private UserService userService;

    @Autowired
    Email emailService;


    @GetMapping("/forgotPassword")
    public String forgotPassword() {
        // Check if the user is logged in or not
        UserDTO user = (UserDTO) session.getAttribute("user_sess");
        if (user != null) {
            return "redirect:/home";
        } else {
            return "login";
        }
    }

    @PostMapping("/forgotPassword")
    public String forgotPasswordProcess(@ModelAttribute UserDTO userDTO, Model model) {
        // Check for primary email
        Employee checkEmail = userService.findByEmail(userDTO.getEmail());
        if (checkEmail != null) {
            try {
                emailService.sendEmail(checkEmail.getEmail());
                return "redirect:/forgotPassword?success"; // Redirect to success page
            } catch (MessagingException e) {
                // Handle email sending failure
                model.addAttribute("error", "Failed to send email. Please try again later.");
                return "redirect:/forgotPassword?error";
            }
        }

        // If no user found with the provided email, return error
        return "redirect:/forgotPassword?error"; // No user found
    }
}
