package com.isp.project.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.isp.project.model.Employee;
import com.isp.project.repositories.EmployeeRepository;
import com.isp.project.service.EmployeeService;
import com.isp.project.service.EmployeeServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private EmployeeService userService;

    @Autowired
    private EmployeeRepository userRepo;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        String email = request.getParameter("username");
        Employee user = userRepo.findByEmail(email);

        if (user != null) {
            if (user.getIsActive()) {
                if (user.isAccountNonLocked()) {
                    if (user.getFailedAttempt() < EmployeeServiceImpl.ATTEMPT_TIME - 1) {
                        userService.increaseFailedAttempt(user);
                    } else {
                        // Check if the user role is receptionist before locking the account
                        if (user.getRole().equals("ROLE_RECEPTIONIST")) {
                            userService.lock(user);
                            exception = new LockedException("Your account is locked! Failed attempt 3");
                        }
                    }
                } else if (!user.isAccountNonLocked()) {
                    if (userService.unlockAccountTimeExpired(user)) {
                        exception = new LockedException("Account is unlocked! Please try to login");
                    } else {
                        exception = new LockedException("Account is locked! Please try after 24 hours");
                    }
                }
            } else {
                exception = new LockedException("Account is inactive...verify account");
            }
        }
        super.setDefaultFailureUrl("/login?error");
        super.onAuthenticationFailure(request, response, exception);
    }
}
