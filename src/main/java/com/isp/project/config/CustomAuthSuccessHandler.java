package com.isp.project.config;

import java.io.IOException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.isp.project.model.Employee;
import com.isp.project.service.EmployeeService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthSuccessHandler implements AuthenticationSuccessHandler {
	@Autowired
	private EmployeeService employeeService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

		CustomUser customUser = (CustomUser) authentication.getPrincipal();
		Employee user = customUser.getUser();

		if (user != null) {
			employeeService.resetAttempt(user.getEmail());
		}

		if (roles.contains("ROLE_ADMIN")) {
			response.sendRedirect("/admin/home");
		} else {
			response.sendRedirect("/receptionist/home");
		}

	}

}
