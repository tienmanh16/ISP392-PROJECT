package com.isp.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.isp.project.dto.UserDTO;
import com.isp.project.dto.UserResetPasswordDto;
import com.isp.project.model.Email;
import com.isp.project.model.Employee;
import com.isp.project.service.EmployeeService;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class ForgotPasswordController {

    @Autowired
    HttpSession session;

    @Autowired
    private EmployeeService userService;

    @Autowired
    Email emailService;

    @GetMapping("/forgotpass")
    public String forgotPassword() {
        // Check if the user is logged in or not
        UserDTO user = (UserDTO) session.getAttribute("user_sess");
        if (user != null) {
            return "redirect:/home";
        } else {
            return "forgotpass";
        }
    }

    @PostMapping("/forgotPassword")
    public String forgotPasswordProcess(@ModelAttribute UserDTO userDTO, Model model) {
        // Check for email
        Employee checkEmail = userService.findByEmail(userDTO.getEmail());
        if (checkEmail != null) {
            try {
                emailService.sendEmail(checkEmail.getEmail(), checkEmail.getPassword());
                session.setAttribute("rawUser", checkEmail);
                model.addAttribute("message", "Password of your account has been sent to Gmail");
                return "forgotpass"; // Redirect to success page
            } catch (MessagingException e) {
                // Handle email sending failure
                model.addAttribute("error", "Failed to send email. Please try again later.");
                return "forgotpass";
            }
        }
        model.addAttribute("error", "Email not found. Please try again later.");
        // If no user found with the provided email, return error
        return "forgotpass";
    }

    @GetMapping("/forgotPassword/resetpassword")
    public String ShowPage(Model model) {
        Employee user = (Employee) session.getAttribute("rawUser");
        if (user == null) {
            return "redirect:/home";
        } else {
            model.addAttribute("employee", new UserResetPasswordDto());
            return "/resetpassword";
        }
    }

    @PostMapping("/forgotPassword/resetpassword")
    public String changePassword(@Valid @ModelAttribute("employee") UserResetPasswordDto userResetPasswordDto,
            BindingResult bindingResult, Model model) {
        Employee rawUser = (Employee) session.getAttribute("rawUser");
        
        if (rawUser == null) {
            return "redirect:/home";
        }

        Employee userId = userService.findById(rawUser.getId());
    
        // Check if the old password input from resetpassword.html matches the one in
        // the database
        if (!userId.getPassword().equals(userResetPasswordDto.getOldPassword())) {
            bindingResult.addError(new FieldError("employee", "oldPassword", "Old password is incorrect!"));
            return "/resetpassword";
        }

        // Check if new passwords match
        if (!userResetPasswordDto.getNewPassword().equals(userResetPasswordDto.getConfirmPassword())) {
            bindingResult.addError(new FieldError("employee", "confirmPassword", "Confirm password does not match!"));
            return "/resetpassword";
        }

        if (bindingResult.hasErrors()) {
            return "/resetpassword";
        }

        // Change the user's password
        userService.changePassword(rawUser.getId(), userResetPasswordDto.getNewPassword());
        model.addAttribute("successMessage", "Password has been successfully changed.");
        return "/resetpassword";
    }
}
