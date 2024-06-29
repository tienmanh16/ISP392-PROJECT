package com.isp.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.isp.project.dto.UserDTO;
import com.isp.project.dto.UserResetPasswordDto;
import com.isp.project.model.Email;
import com.isp.project.model.Employee;
import com.isp.project.service.EmployeeService;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/forgotpass")
public class ForgotPasswordController {

    @Autowired
    HttpSession session;

    @Autowired
    private EmployeeService userService;

    @Autowired
    private Email emailService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("")
    public String forgotPassword() {
        return "forgotpass";
    }

    @PostMapping("")
    public String forgotPasswordProcess(@ModelAttribute UserDTO userDTO, Model model) {
        Employee checkEmail = userService.findByEmail(userDTO.getEmail());
        if (checkEmail != null) {
            try {
                emailService.sendEmail(checkEmail.getEmail(), checkEmail.getVerifyCode());
                session.setAttribute("rawUser", checkEmail);
                model.addAttribute("message", "Password reset instructions have been sent to your email.");
                return "forgotpass";
            } catch (MessagingException e) {
                model.addAttribute("error", "Failed to send email. Please try again later.");
                return "forgotpass";
            }
        }
        model.addAttribute("error", "Email not found. Please try again.");
        return "forgotpass";
    }

    @GetMapping("/resetpassword")
    public String showResetPasswordPage(Model model) {
        Employee user = (Employee) session.getAttribute("rawUser");
        if (user == null) {
            return "redirect:/home";
        } else {
            model.addAttribute("employee", new UserResetPasswordDto());
            return "resetpassword";
        }
    }

    @PostMapping("/resetpassword")
    public String changePassword(@Valid @ModelAttribute("employee") UserResetPasswordDto userResetPasswordDto,
            Model model) {

        Employee rawUser = (Employee) session.getAttribute("rawUser");
        if (rawUser == null) {
            return "redirect:/home";
        }

        Employee userId = userService.findById(rawUser.getId());
        // System.out.println("VerifyCode from database: " + userId.getVerifyCode());
        // System.out.println("VerifyCode from form: " + userResetPasswordDto.getVerifyCode());

        // Check if the verification code input matches the one in the database
        if (!userId.getVerifyCode().equals(userResetPasswordDto.getVerifyCode().replaceAll("\\s+", ""))) {
            model.addAttribute("error", "Verification code incorrect!");
            return "resetpassword";
        }

        // Change the user's password
        userService.changePassword(rawUser.getId(), userResetPasswordDto);
        model.addAttribute("successMessage", "Password has been successfully changed.");

        return "resetpassword";
    }
}
