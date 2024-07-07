package com.isp.project.config;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.isp.project.model.Employee;
import com.isp.project.service.EmployeeService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    private static final Logger logger = LoggerFactory.getLogger(CustomSuccessHandler.class);

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        if (authentication.getPrincipal() instanceof DefaultOidcUser) {
            DefaultOidcUser oidcUser = (DefaultOidcUser) authentication.getPrincipal();
            String email = oidcUser.getEmail();
            Employee user = employeeService.findByEmail(email);

            if (user != null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authToken);

                logger.info("User {} authenticated successfully via OAuth2.", user.getEmail());

                if (user.getRole().equals("ROLE_ADMIN")) {
                    response.sendRedirect("/admin/home");
                } else if (user.getRole().equals("ROLE_RECEPTIONIST")) {
                    response.sendRedirect("/receptionist/home");
                } else {
                    response.sendRedirect("/home");
                }
            } else {
                logger.warn("User not found for email: {}", email);
                // Set error message to display on login page
                request.setAttribute("loginError", "User not found");
                request.getRequestDispatcher("/login").forward(request, response);
            }
        }
    }
}